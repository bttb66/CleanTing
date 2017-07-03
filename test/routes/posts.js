const express = require('express');
const router = express.Router();
const pool = require('../config/db_pool.js');
const aws = require('aws-sdk');
//aws.config.loadFromPath('../../config/aws_config.json');
const s3 = new aws.S3();
const moment = require('moment');
// const jwt = require('jsonwebtoken');

//게시글 작성
router.post('/', async(req, res) => {
  try {
    //필요한데이터 넣지않으면 오류발생처리
    if(!(req.body.title&&req.body.content&&req.body.userId))
      res.status(403).send({ message: 'please input all of title, content, userId.'});
    //데이터 다 넣으면 실행
    else {
      var connection = await pool.getConnection();
      //post 테이블에 게시글 데이터 삽입
      let query1='insert into post set ?';
      let record = {
           userId : req.body.userId,
           title: req.body.title,
           content: req.body.content,
           view_number : 0,
           date : moment(new Date()).format('YYYY-MM-DD'),
           time : moment(new Date()).format('h:mm:ss a'),
           comment_cnt : 0
        };
        await connection.query(query1, record);
        //성공시
      res.status(200).send({
          "message" : "Succeed in writing a post"
      });
     }
  }
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

//댓글작성
router.post('/:postId', async(req, res) => {
  try {
    //필요한데이터 넣지않으면 오류발생처리
    if(!(req.body.content&&req.body.userId))
      res.status(403).send({ message: 'please input all of content, userId'});
    //안넣으면
    else {
      var connection = await pool.getConnection();
      //comment 테이블에 데이터 삽입
      let query1='insert into comment set ?';
      let record = {
          userId : req.body.userId,
          postId : req.params.postId,
          date : moment(new Date()).format('YYYY-MM-DD'),
          time : moment(new Date()).format('h:mm:ss a'),
          content: req.body.content
        };
        await connection.query(query1, record);
        //댓글달때 post 테이블의 댓글갯수칼럼 수 증가
        let query2 = 'update post set comment_cnt = comment_cnt + 1 where postId=?';
        await connection.query(query2, req.params.postId);
      res.status(200).send({
          "message" : "Succeed in writing a comment"
      });
     }
  }
  catch(err){
      console.log(err);
      res.status(500).send({
        "message": "syntax error :"[err]
      });
      await connection.rollback();
  }
  finally{
      pool.releaseConnection(connection);
  }
});

//전체 게시글 조회
router.get('/', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        let query = 'select * from post order by postId desc';
        var post =  await connection.query(query);
        res.status(200).send({
            "message" : "전체게시글 조회에 성공하였습니다",
            "result" : post
        });
    }
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

//특정 게시글 조회
router.get('/:postId', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        //조회수 1늘리기
        let query1 = 'update post set view_number = view_number + 1 where postId=?';
        await connection.query(query1, req.params.postId);
        //게시글 가져오기
        let query2 = 'select * from post where postId=?';
        let post = await connection.query(query2, req.params.postId);
        //게시글에 달린 댓글들 가져오기
        let query3 = 'select * from comment where postId=?';
        let comments = await connection.query(query3, req.params.postId);
        res.status(200).send({
            "message" : "게시물 상세조회에 성공하였습니다.",
            "result" : { "post": post[0],
                      "comment": comments }
        });
    }
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

//게시판 검색(단어를 포함하는 글만 조회)
router.get('/search/:key', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        //검색어
        var key = req.params.key;
        //검색어와 일치하는 게시판 가져오기(제목과 내용이 일치하게)
        let query = "select * from post where title like '%"+key+"%' or content like '%"+key+"%' order by postId desc";
        var search =  await connection.query(query);
        res.status(200).send({
            "message" : "Succeed in searching a post",
            "result" : search
        });
    }
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

//내가 작성한 게시글만 모아보기
router.get('/member/:userId', async (req, res) => {
    try {
        var connection = await pool.getConnection();
        //사용자가 작성한 글만 모아 가져오기
        let query = 'select * from post where userId=? order by postId desc';
        var post =  await connection.query(query, req.params.userId);
        res.status(200).send({
            "message" : "내가 작성한 게시글 조회에 성공하였습니다",
            "result" : post
        });
    }
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

module.exports = router;
