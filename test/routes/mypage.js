const express = require('express');
const router = express.Router();
const pool = require('../config/db_pool.js');
const aws = require('aws-sdk');
//aws.config.loadFromPath('../../config/aws_config.json');
const s3 = new aws.S3();
const jwt = require('jsonwebtoken');
const moment = require('moment');

//이용내역 조회
router.get('/usage/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    let query = 'select * from user_usage where userId=? order by usageId desc';
    let result = await connection.query(query, req.params.userId);
    res.status(200).send({message:'ok', ret:result});
  }
  catch(err){
    console.log(err);
    res.status(500).send({message:'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//회원 정보 수정(핸드폰 번호)
router.put('/phone/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const userId = req.params.userId;
    const phone = req.body.phone;
    let query = 'update user set phone=? where userId=?';
    await connection.query(query , [phone, userId]);

    res.status(200).send({message:'phone update ok'});
  }
  catch(err){
    console.log(err);
    res.status(500).send({message: 'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//회원 정보 수정(주소)
router.put('/address/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const userId = req.params.userId;
    const address = req.body.address;
    const locationNum = req.body.locationNum;

    let record={
      address : address,
      locationNum : locationNum
    };
    let query = "update user set ? where userId='"+ userId+"'";
    await connection.query(query, record);

    let record2 = {
      lat: req.body.userLat,
      lng : req.body.userLng
    }
    let query2 = "update map_info set ? where userId='"+ userId+"'";
    await connection.query(query2, record2);

    res.status(200).send({message:'address update ok'});
  }
  catch(err){
    console.log(err);
    res.status(500).send({message: 'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//회원 정보 수정(비밀번호)
router.put('/pwd/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    const userId = req.params.userId;
    const pwd = req.body.pwd;
    let query = 'update user set pwd=? where userId=?';
    await connection.query(query , [pwd, userId]);

    res.status(200).send({message:'pwd update ok'});
  }
  catch(err){
    console.log(err);
    res.status(500).send({message: 'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//탈퇴하기
router.delete('/withdraw/:userId', async (req, res)=>{
  try{
    //신청한 팀 있는지 확인 후 있을 경우 cnt-1, cascade로 삭제
    var connection = await pool.getConnection();
    const userId = req.params.userId;
    //신청한 팅이 있을 경우 해당 팅 cnt -1
    let query = 'update user_ting natural join ting set cnt = cnt-1 where userId=?';
    await connection.query(query, userId);

    let query2 = 'delete from user where userId=?';
    await connection.query(query2, userId);
    res.status(200).send({message:'user withdraw success'});
  }
  catch(err){
    console.log(err);
    res.status(500).send({message:'server err: '+err});
    await connection.rollback();
  }
  finally{
    pool.releaseConnection(connection);
  }
});

module.exports = router;
