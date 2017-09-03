const express = require('express');
const router = express.Router();
const pool = require('../config/db_pool.js');
const aws = require('aws-sdk');
//aws.config.loadFromPath('../../config/aws_config.json');
const moment = require('moment');
const FCM = require('fcm-push');
var serverKey = require('../config/serverKey').serverKey;
var fcm = new FCM(serverKey);

//token, device 수정
function callAlarm(token, device, msg){
  var message = {
      to: token, // required fill with device token or topics
      collapse_key: 'your_collapse_key',
      data: {
          your_custom_data_key: msg //메세지 날리는 경우
          //팅에 입장할 때 = userId+"님이 팅에 참가하였습니다."
          //팅을 취소했을 때  = userId+"님이 팅을 나갔습니다."
          //클리너가 팅을 승인했을 때
          //클리너가 청소를 거절했을 떄
          //클리너가 청소를 완료했을때
          //클리너가 청소를 시작했을 때
          //청소시작 하루전에 청소결제를 해야할 때
      },
      notification: {
          title: 'Title of your push notification',
          body: 'Body of your push notification'
      }
  };

  // function saveAlarm (connection, tingId, msg){
  //   let query = 'insert into alarm set ?';
  //   let record = {
  //     tingId : tingId,
  //     content : msg
  //   };
  //   await connection.query(query, record);
  // }

  //promise style
  fcm.send(message)
      .then(function(response){
          console.log("Successfully sent with response: ", response);
      })
      .catch(function(err){
          console.log("Something has gone wrong!");
          console.error(err);
      });
}

module.exports = router;
