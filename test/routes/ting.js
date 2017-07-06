const express = require('express');
const aws = require('aws-sdk');
const router = express.Router();
const pool = require('../config/db_pool.js');
//aws.config.loadFromPath('../../config/aws_config.json');
const s3 = new aws.S3();
const jwt = require('jsonwebtoken');
const moment = require('moment');
const FCM = require('fcm-push');
var serverKey = require('../config/serverKey').serverKey;
var fcm = new FCM(serverKey);

//팅 만들기
router.post('/', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const userId = req.body.userId;
    const msg = userId+"님이 팅에 참가하였습니다.";

    let record = {
      date : req.body.date,
      startTime : req.body.startTime,
      endTime : req.body.endTime,
      cnt : '1',
      cleanerId : req.body.cleanerId
    };
    //ting 테이블에 팅정보 새로 삽입
    let query = 'insert into ting set ?';
    var inserted = await connection.query(query, record);
    console.log(inserted);
    //사용자 팅 등록정보 삽입(user_ting)
    let query2 = 'insert into user_ting set ?';
    var record2 = {
      userId : userId,
      tingId : inserted.insertId,
      price :req.body.price,
      request : req.body.request,
      warning : req.body.warning
    };
    await connection.query(query2, record2);

    //맵에 ting의 위도경도추가
    let query3='insert into map_info set ?';
    let record3 = {
         tingId : inserted.insertId,
         lat : req.body.lat,
         lng : req.body.lng
      };
     await connection.query(query3, record3);

    //알람부르기 & 메세지전송 & 저장
    // push.callAlarm(token, device, msg); //token, device 수정
    //
    // let query4 = 'insert into alarm set ?';
    // let record4 = {
    //   tingId : tingId,
    //   content : msg
    // };
    // await connection.query(query4, record4);

    res.status(200).send({message:'팅 생성 성공'});
  }
  catch (err){
    console.log(err);
    res.status(500).send({message : "server err : "+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//팅 신청하기
router.post('/:tingId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const tingId = req.params.tingId;
    const userId = req.body.userId;
    const msg = userId+"님이 팅에 참가하였습니다.";

    //신청한 팅의 인원수가 3명 이하인지 확인
    let query = 'select cnt from ting where tingId=?';
    var cnt = await connection.query(query, tingId);
    if(cnt[0].cnt > 2){
      res.status(400).send({message:'인원이 전부 찼습니다.'})
    }
    else{
      await connection.beginTransaction();
      //신청한 팅의 신청인원 변경(+1)
      let query2 = 'update ting set cnt = cnt + 1 where tingId=?';
      await connection.query(query2, tingId);

      let query3 = 'insert into user_ting set ?';
      let record = {
        userId : userId,
        tingId : tingId,
        price :req.body.price,
        request : req.body.request,
        warning : req.body.warning
      };
      await connection.query(query3, record);

      //알림 받을 사용자들의 토큰 가져오기
      let query4 ='select user.deviceToken from user natural join user_ting where user_ting.tingId=?'
      let result = await connection.query(query4, tingId);
      let ids=[];
      console.log('result : ',result);
      for(var i in result){
        ids.push(result[i].deviceToken);
      }

      console.log('ids: ',ids);
      //알림 보내기
      //알람부르기 & 메세지전송 & 저장
      var message = {
        registration_ids: ids , // required fill with device token or topics
        notification: {
            title: 'Cleanting',
            body: req.body.userName+'님이 팅에 참가하셨습니다.'
        }
      };
      console.log(serverKey);
      fcm.send(message)
          .then(function(response){
            console.log(message);
              console.log("Successfully sent with response: ", response);
          })
          .catch(function(err){
              console.log("Something has gone wrong!");
              console.error(err);
          });

      //알림 테이블에 알림 내용 저장
      let record2 = {
        tingId: tingId,
        content: msg,
        time: moment(new Date()).format('h:mm a')
      };
      await connection.query('insert into alarm set ?', record2);

      res.status(200).send({message:'팅 신청 성공'});
      await connection.commit();
    }
  }
  catch(err){
    res.status(500).send({message:'server error: '+err});
    await connection.rollback();
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//팅 조회하기(사용자 지역별)
router.post('/area/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const userId = req.params.userId;
    //const order = req.headers.order;

    const userLat = req.body.userLat;
    const userLng = req.body.userLng;

    let query2 = ''+
    'SELECT ting.*, cleaner.*,'+
     '(6371*acos(cos(radians(?))*cos(radians(lat))*cos(radians(lng)'+
     '-radians(?))+sin(radians(?))*sin(radians(lat))))'+
     'AS distance'+
    ' FROM map_info'+
    ' join ting join cleaner'+
    ' WHERE ting.tingId=map_info.tingId'+
    ' and ting.cleanerId=cleaner.cleanerId'+
    ' and ting.tingId not in (select tingId from user_ting where userId=?)'+
    ' HAVING distance <= 0.1'+
    ' ORDER BY ting.cnt desc';

    var result = await connection.query(query2, [userLat, userLng, userLat, userId]);
    //lat : 위도, lng : 경도
    res.status(200).send({message:'OK', result:result});

  }
  catch (err){
    console.log(err);
    res.status(500).send({ message:'server err: '+err });
  }
  finally{
    pool.releaseConnection(connection);
  }
});


//팅 수정하기 (body -> price, ,userId, request:0,1,2,3)
router.put('/:tingId', async (req, res)=>{
  try{
    let request = req.body.request;
    if(!request || request < 0 || request > 3){
      res.status(400).send({message:'request param err'});
    } else{
      var connection = await pool.getConnection();
      let query = 'update user_ting set request =? where tingId=? and userId=?';
      await connection.query(query, [request, req.params.tingId, req.body.userId]);
      let query2 = 'update user_ting set price =? where tingId=? and userId=?';
      await connection.query(query2, [req.body.price, req.params.tingId, req.body.userId]);
      let query3 = 'update user_ting set warning =? where tingId=? and userId=?';
      await connection.query(query3, [req.body.warning, req.params.tingId, req.body.userId]);
      res.status(200).send({message:'팅 수정 성공'});
    }
  }
  catch (err){
    res.status(500).send({message:'server err :'+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});


//사용자 신청 팅 조회하기
router.get('/register/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const userId = req.params.userId;
    let query = ''+
    'select * from ting natural join user_ting'+
    ' natural join cleanting.cleaner'+
    ' where userId=?'
    var ret = await connection.query(query, userId);
    res.status(200).send({message:'사용자가 신청한 팅 조회 성공', result:ret});
  }
  catch(err){
    console.log(err);
    res.status(500).send({message:'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

// //팅 상세정보 조회
 // router.get('/detail/:tingId', async (req, res)=>{
 //   try{
 //     var connection = await pool.getConnection();
 //     let userId = req.headers.userId;
 //     let tingId = req.params.tingId;
 //     let query = 'select * from user_ting where userId=? and tingId=?';
 //
 //     var result = await connection.query(query, [req.userId, tingId]);
 //     res.status(200).send({message : '팅 상세 정보조회 성공', result : result});
 //   }
 //   catch(err){
 //     console.log(err);
 //     res.status(500).send({message:'server err: '+err});
 //   }
 //   finally{
 //     pool.releaseConnection(connection);
 //   }
 // });

 //팅 완료
 router.get('/complete/:tingId', async (req, res)=>{
   try{
     var connection = await pool.getConnection();
     const tingId = req.params.tingId;

     let query = ""+
    "insert into user_usage(userId, cleanerId, date, startTime, endTime)"+
    " select user_ting.userId, ting.cleanerId, ting.date, ting.startTime, ting.endTime"+
    " from ting natural join user_ting"+
    " where ting.tingId=?";
    await connection.query(query, tingId);

    //완료한 팅 관련 정보 삭제
    await connection.query('delete from ting where tingId=?', tingId);
    res.status(200).send({message:'팅 완료 성공'});
   }
   catch (err){
     res.status(500).send({message:'server err: '+err});
     await connection.rollback();
   }
   finally{
     pool.releaseConnection(connection);
   }
 });


//팅취소하기
router.delete('/:tingId', async (req, res)=>{
  try{
      if(!req.body.userId){
        res.status(403).send({message:'please input userId.'});
      }else{
        var connection = await pool.getConnection();
        const tingId = req.params.tingId;
        const userId = req.body.userId;
        const msg = userId+"님이 팅을 나갔습니다.";
        let query = 'select cnt from ting where tingId=?';
        const cnt = await connection.query(query, tingId);

        if(cnt[0].cnt == 1){
          let queryDel = 'delete from ting where tingId=?';
          await connection.query(queryDel, tingId);
        }else{
          await connection.beginTransaction();
          //user_ting 테이블의 데이터 삭제
          let query1 = 'delete from user_ting where userId=? and tingId=?';
          await connection.query(query1, [userId, tingId]);

          //취소한 팅의 인원 줄이기
          let query2 = 'update ting set cnt = cnt - 1 where tingId=?';
          await connection.query(query2, tingId);

            //알림 받을 사용자들의 토큰 가져오기
          let query4 ='select user.deviceToken from user natural join user_ting where user_ting.tingId=?'
          let result = await connection.query(query4, tingId);
          let ids=[];
          console.log('result : ',result);
          for(var i in result){
            ids.push(result[i].deviceToken);
          }

          console.log('ids: ',ids);
          //알림 보내기
          //알람부르기 & 메세지전송 & 저장
          var message = {
            registration_ids: ids , // required fill with device token or topics
            notification: {
                title: 'Cleanting',
                body: req.body.userName+'님이 팅에서 나가셨습니다.'
            }
          };
          console.log(serverKey);
          fcm.send(message)
              .then(function(response){
                console.log(message);
                  console.log("Successfully sent with response: ", response);
              })
              .catch(function(err){
                  console.log("Something has gone wrong!");
                  console.error(err);
              });

          //알림 테이블에 알림 내용 저장
          let record2 = {
            tingId: tingId,
            content: msg,
            time: moment(new Date()).format('h:mm a')
          };
          await connection.query('insert into alarm set ?', record2);
          await connection.commit();
        }
        res.status(200).send({message:'팅 취소 성공'});
      }//else문 끝
    }//try문 끝
    catch (err){
      res.status(500).send({message:'server err: '+err});
      await connection.rollback();
    }
    finally{
      pool.releaseConnection(connection);
    }
  });


module.exports = router;
