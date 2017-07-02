const express = require('express');
const router = express.Router();
const pool = require('../config/db_pool.js');
const aws = require('aws-sdk');
//aws.config.loadFromPath('../../config/aws_config.json');
const s3 = new aws.S3();
const jwt = require('jsonwebtoken');

//사용자 알람 정보 조회
router.get('/:userId', async (req, res)=>{
  try{
    var connection = await pool.getConnection();
    let query = 'select alarm.* from user_ting natural join alarm where userId=?';
    var result = await connection.query(query, req.params.userId);

    res.status(200).send({message:'alarm query ok', ret:result});
  }
  catch (err){
    res.status(500).send({message:'server err:'+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});

//알람 받기 여부 수정(1, -1)
router.put('/:push', async(req, res)=>{
  try{
    var connection = await pool.getConnection();
    let query = 'update user set push=-1*push';
    await connection.query(query);
    res.status(200).send({message:'push update ok'});
  }
  catch (err){
    console.log(err);
    res.status(500).send({message:'server err: '+err});
  }
  finally{
    pool.releaseConnection(connection);
  }
});


module.exports = router;
