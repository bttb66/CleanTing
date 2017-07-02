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
    const userId = req.params.getId
    const phone = req.headers.phone;
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
    const userId = req.params.getId
    const address = req.headers.address;
    let query = 'update user set address=? where user_id=?';
    await connection.query(query, [address, userId]);

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
router.put('/phone/:userId', async (req, res)=>{
  try{
    const userId = req.params.getId
    const password = req.headers.password;
    let query = 'update user set password=? where user_id=?';
    await connection.query(query , [password, userId]);

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

module.exports = router;
