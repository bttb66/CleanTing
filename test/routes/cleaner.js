const express = require('express');
const aws = require('aws-sdk');
const router = express.Router();
//aws.config.loadFromPath('../config/aws_config.json');
const pool = require('../config/db_pool.js');
const s3 = new aws.S3();
const moment = require('moment');
const jwt = require('jsonwebtoken');
const multer = require('multer');
const multerS3 = require('multer-s3');
const upload = multer({
  storage: multerS3({
    s3: s3,
    bucket: 'hatemush',
    acl: 'public-read', //이미지 읽기만 허용
    key: function(req, file, cb){
      cb(null, Date.now() + '.' + file.originalname.split('.').pop());
    }
  })
});

//클리너등록(사진등록)
router.post('/register', upload.single('image'),async(req, res) => {
  try {
    //필요한데이터 넣지않으면 오류발생처리
    if(!(req.body.cleanerId&&req.body.name&&req.body.phone&&req.body.age&&req.body.career&&req.body.area))
      res.status(403).send({ message: 'please input all of cleanerId, name, phone, age, career, area.'});
    //데이터 다 넣으면 실행
    else {
      var connection = await pool.getConnection();
      const cleanerId = req.body.cleanerId;
      //cleaner 테이블에 클리너 데이터 삽입
      let query1='insert into cleaner set ?';
      let imageUrl;
      if(!req.file) imageUrl = null;
      else imageUrl = req.file.location;
      let record = {
           cleanerId: req.body.cleanerId,
           name: req.body.name,
           phone : req.body.phone,
           age : req.body.age,
           career : req.body.career,
           area : req.body.area,
           rate : 0,
           review_cnt : 0,
           image: imageUrl
        };
        await connection.query(query1, record);

        //맵에 cleaner의 위도경도추가
        let query2='insert into map_info set ?';
        let record2 = {
             cleanerId : cleanerId,
             lat : req.body.lat,
             lng : req.body.lng
          };
         await connection.query(query2, record2);

        //성공시
      res.status(200).send({
          "message" : "Succeed in registering a cleaner"
      });
    }//else문끝
  }//try문 끝
  catch(err){
      console.log(err);
      res.status(500).send({
        "message": "syntax error :" [err]
      });
      await connection.rollback();
  }
  finally{
      pool.releaseConnection(connection);
  }
});

//클리너검색
router.get('/search/:key', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        //검색어변수
        var key = req.params.key;
        //검색어와 일치하는 클리너 가져오기
        let query = "select * from cleaner where name like '%"+key+"%'";
        var search =  await connection.query(query);
        res.status(200).send({
            "message" : "Succeed in searching a cleaner",
            "result" : { "search": search }
        });
    }//try문끝
    catch(err){
        console.log(err);
        res.status(500).send({
          "message": "syntax error : " [err]
        });
        await connection.rollback();
    }
    finally{
        pool.releaseConnection(connection);
    }
});

//최근이용클리너(최근순)
router.get('/lately/:userId', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        //최근이용클리너 데이터 가져오기
        let query = "select cleaner.* from cleaner natural join user_usage where userId=? order by user_usage.usageId desc";
        var lately =  await connection.query(query, req.params.userId);
        res.status(200).send({
            "message" : "클리너 최근이용순으로 정렬 성공",
            "result" : { "lately": lately }
        });
    }//try문끝
    catch(err){
        console.log(err);
        res.status(500).send({
          "message": "syntax error : " [err]
        });
        await connection.rollback();
    }
    finally{
        pool.releaseConnection(connection);
    }
});

// 지역별 클리너 정보(필터링 - 클리너 팅 신청 날짜 확인)
// 클리너 팅 받아온 날짜, 별점순 1, 이력순 2, 리뷰순 3
router.post('/:date', async (req, res)=>{
  try{
    var connection = await pool.getConnection();

    const date  = req.params.date;
    const userId = req.body.userId;
    const order = req.body.order;
    //let query = 'select cleaner.* from cleaner join ting where area=? date != ? order by ting.t_id desc';
    if(order!=1&&order!=2&&order!=3){
      res.status(400).send({message:'order parameter err'});
    } else{
      let query = 'select lat, lng from map_info where userId=?'; //사용자 지역정보 가져오기
      var area = await connection.query(query, userId);
      const userLat = area[0].lat;
      const userLng = area[0].lng;
      let orderBy = "";

      if(order == 1){ //별점순
        orderBy = "rate/review_cnt";
      } else if(order == 2){  //이력순
        orderBy = "career";
      } else if(order == 3){ //리뷰순
        orderBy = "review_cnt";
      }
      let query2 = ''+
      'SELECT ting.*,'+
      '(6371*acos(cos(radians(?))*cos(radians(lat))*cos(radians(lng)'+
      '-radians(?))+sin(radians(?))*sin(radians(lat))))'+
      'AS distance'+
      'FROM MAP_INFO'+
      'natural join cleaner'+
      'WHERE not map_info.cleanerId is NULL'+
      'HAVING distance <= 0.1'+
      'ORDER BY '+ orderBy + ' desc '
      'LIMIT 0,30';

      var ret = await connection.query(query2, [userLat, userLng, userLat]);
      res.status(200).send({message:'ok', result:ret});
    }
  }
  catch(err){
    res.status(500).send({message:'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

// 클리너 팅 받아온 날짜, 정렬(최신순), 이력순, 리뷰순
router.post('/:date', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const date  = req.params.date;
    const area = req.body.area;
    let query = 'select cleaner.* from cleaner join ting where area=? date != ? order by ting.tingId desc';
    var ret = await connection.query(query, [area, date]);
    res.status(200).send({message:'ok', result:ret});
  }
  catch(err){
    res.status(500).send({message:'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//클리너 상세정보조회
router.get('/detail/:cleanerId', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        //클리너정보가져오기
        let query1 = 'select * from cleaner where cleanerId=?';
        let cleaner = await connection.query(query1, req.params.cleanerId);
        //클리너리뷰가져오기
        let query2 = 'select * from cleaner_review where cleanerId=? order by reviewId desc';
        let review = await connection.query(query2, req.params.cleanerId);

        res.status(200).send({
            "message" : "클리너 상세조회에 성공하였습니다",
            "result" : { "cleaner": cleaner[0],
                         "review": review }
        });
    }//try문 끝
    catch(err){
        console.log(err);
        res.status(500).send({
          "message": "syntax error" [err]
        });
        await connection.rollback();
    }
    finally{
        pool.releaseConnection(connection);
    }
});

//클리너 리뷰 작성
router.post('/review/:cleanerId', async(req, res) => {
  try {
    //필요한데이터 넣지않으면 오류발생처리
    if(!(req.body.content&&req.body.userId&&req.body.rating))
      res.status(403).send({ message: 'please input all of content, rating, userId'});
    //데이터 다 넣으면 실행
    else {
      var connection = await pool.getConnection();
      const cleanerId = req.params.cleanerId;
      //별점에 참여한 인원 수 1늘리기
      let query1 = 'update cleaner set review_cnt = review_cnt + 1 where cleanerId=?';
      await connection.query(query1, cleanerId);
      //별점 합계 늘려주기
      let query2 = 'update cleaner set rate = rate+? where cleanerId=?';
      await connection.query(query2, [req.body.rating, cleanerId]);
       //클리너에 대한 리뷰작성
       let query3='insert into cleaner_review set ?';
       let record = {
            userId  : req.body.userId,
            cleanerId : cleanerId,
            date  : moment(new Date()).format('YYYY-MM-DD'),
            rating  : req.body.rating,
            content : req.body.content
         };
        await connection.query(query3, record);

        //성공시
      res.status(200).send({
          "message" : "Succeed in writing a cleaner review"
      });
    }//else문 끝
  } //try문 끝
  catch(err){
      console.log(err);
      res.status(500).send({
        "message": "syntax error :" [err]
      });
      await connection.rollback();
  }
  finally{
      pool.releaseConnection(connection);
  }
});

module.exports = router;
