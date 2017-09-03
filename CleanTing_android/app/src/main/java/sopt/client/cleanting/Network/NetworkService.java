package sopt.client.cleanting.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sopt.client.cleanting.Alarm.AlarmOnOffResult;
import sopt.client.cleanting.Alarm.ReferAlarmResult;
import sopt.client.cleanting.Cleanner.AddCleanerReviewResult;
import sopt.client.cleanting.Cleanner.CleanerDateData;
import sopt.client.cleanting.Cleanner.SearchCleanerDetailResult;
import sopt.client.cleanting.Cleanner.SearchCleanerResult;
import sopt.client.cleanting.Cleanner.SearchLocationCleanerResult;
import sopt.client.cleanting.Cleanner.SendSearchLocationCleanerData;
import sopt.client.cleanting.Community.BulletinAddPostData;
import sopt.client.cleanting.Community.BulletinAddPostResult;
import sopt.client.cleanting.Community.FindAllBulletinResult;
import sopt.client.cleanting.Community.FindBulletinResult;
import sopt.client.cleanting.Community.Reply.BulletinAddCommentData;
import sopt.client.cleanting.Community.Reply.BulletinAddCommentResult;
import sopt.client.cleanting.Community.SearchBulletinResult;
import sopt.client.cleanting.Community.SendSearchBulletinData;
import sopt.client.cleanting.Main.Login.FindIdResult;
import sopt.client.cleanting.Main.Login.FindPhoneNumberResult;
import sopt.client.cleanting.Main.Login.LoginResult;
import sopt.client.cleanting.Main.Login.SendLoginData;
import sopt.client.cleanting.Main.SignUp.IdCheckResult;
import sopt.client.cleanting.Main.SignUp.SignUpData;
import sopt.client.cleanting.Main.SignUp.SignUpResult;
import sopt.client.cleanting.MakeTing.EndTingData;
import sopt.client.cleanting.MakeTing.EndTingResult;
import sopt.client.cleanting.MakeTing.MakeTingLocationResult;
import sopt.client.cleanting.MakeTing.MakeTingRequestResult;
import sopt.client.cleanting.MakeTing.MakeTingRequestResultData;
import sopt.client.cleanting.MakeTing.MakeTingResult;
import sopt.client.cleanting.MakeTing.MakeTingResultData;
import sopt.client.cleanting.MakeTing.RecentCleaner.RecentCleanerResult;
import sopt.client.cleanting.MakeTing.SendTingLocationData;
import sopt.client.cleanting.MyRequest.MyRequestTingEditData;
import sopt.client.cleanting.MyRequest.MyRequestTingEditResult;
import sopt.client.cleanting.MyRequest.RequestTingDetailResult;
import sopt.client.cleanting.Mypage.ModifyPasswordResult;
import sopt.client.cleanting.Mypage.ModifyUserAddressData;
import sopt.client.cleanting.Mypage.ModifyUserAddressResult;
import sopt.client.cleanting.Mypage.ModifyUserPhoneResult;
import sopt.client.cleanting.Mypage.MyhistoryResult;
import sopt.client.cleanting.Mypage.SendAddCleanerReviewData;
import sopt.client.cleanting.Mypage.WithdrawResult;

/**
 * Created by 김지원 on 2017-06-25.
 */

public interface NetworkService {
    /**api 문서를 기반으로 숫자를 써주자*/
    // 1-1 회원가입
    @POST("members/signUp")
    Call<SignUpResult> getSignUpResult(@Body SignUpData signUpData);

    // 1-2 아이디 중복확인
    @GET("members/duplicate/{id}")
    Call<IdCheckResult> getIdCheckResult(@Path("id") String id);

    // 1-3 로그인
    @POST("members/login")
    Call<LoginResult> getLoginResult(@Body SendLoginData sendLoginData);

    // 1-4 아이디 찾기
    @GET("members/id/{phone}")
    Call<FindIdResult> getFindIdResult(@Path("phone") String phone);

    // 1-5 핸드폰 번호 인증
    @PUT("members/phone/{phone}")
    Call<FindPhoneNumberResult> getFindPhoneNumberResult(@Path("phone") String phone);

    // 1-6 비밀번호 수정
    @PUT("members/pwd/{phone}")
    Call<ModifyPasswordResult> getModifyPasswordResult(@Path("pwd") String pwd);

    // 2-1 팅 생성하기
    @POST("ting")
    Call<MakeTingResult> getMakeTingResult(@Body MakeTingResultData makeTingResultData);

    // 2-2 팅 신청하기
    @POST("ting/{tingId}")
    Call<MakeTingRequestResult> getMakeTingRequestResult(@Path("tingId") String tingId, @Body MakeTingRequestResultData makeTingRequestResultData);

    // 2-3 팅 조회(지역)
    @POST("ting/area/{userId}")
    Call<MakeTingLocationResult> getMakeTingLocationResult(@Path("userId") String userId, @Body SendTingLocationData sendTingLocationData);

    // 2-4 팅 수정
    @PUT("ting/{tingId}")
    Call<MyRequestTingEditResult> getMyRequestTingEditResult(@Path("tingId") String tingId, @Body MyRequestTingEditData myRequestTingEditData);

    // 2-5 사용자 신청 팅 조회
    @GET("ting/register/{userId}")
    Call<RequestTingDetailResult> getRequestTingDetailResult(@Path("userId") String userId);

    // 2-6 팅 완료
    @GET("ting/complete/{tingId}")
    Call<EndTingResult> getCompleteResult(@Path("tingId") String tingId);

    // 2-7 팅 취소하기
    @HTTP(method = "DELETE", path = "ting/{tingId}", hasBody = true)
    Call<EndTingResult> getCancelTingResult(@Path("tingId") String tingId, @Body EndTingData endTingData);

    // 3-1 전체 게시글 조회
    @GET("posts/")
    Call<FindAllBulletinResult> getFindAllBulletinResult(@Query("locationNum") int locationNum);

    // 3-2 특정 게시글 조회
    @GET("posts/{postId}")
    Call<FindBulletinResult> getFindBulletinResult(@Path("postId") String postId);

    // 3-3 게시글 작성
    @POST("posts")
    Call<BulletinAddPostResult> getBulletinAddPostResult(@Body BulletinAddPostData bulletinAddPostData);

    // 3-4 댓글 작성
    @POST("posts/{postId}")
    Call<BulletinAddCommentResult> getBulletinAddCommentResult(@Path("postId") String postId ,@Body BulletinAddCommentData bulletinAddCommentData);

    // 3-5 게시판 검색
    @POST("posts/search/{key}")
    Call<SearchBulletinResult> getSearchBulletinResult(@Path("key") String key, @Body SendSearchBulletinData sendSearchBulletinData);

    // 3-6 내가 작성한 게시글만 모아보기
    @GET("posts/member/{userId}")
    Call<SearchBulletinResult> getSearchMyBulletinResult(@Path("userId") String userId);

    // 4-1 클리너 등록
    /** 클리너용 앱이 아니기때문에 과감히 생략 */

    // 4-2 클리너 검색
    @GET("cleaner/search/{key}")
    Call<SearchCleanerResult> getSearchCleanerResult(@Path("key") String key);

    // 4-3 최근 이용 클리너(최신순)
    /**API 수정중*/
    @GET("cleaner/lately/{userId}")
    Call<RecentCleanerResult> getRecentCleanerResult(@Path("userId") String key);

    // 4-4 지역별 클리너(별점순/이력순/후기순)
    @POST("cleaner/{date}")
    Call<SearchLocationCleanerResult> getSearchLocationCleanerResult(@Path("date") String date, @Body SendSearchLocationCleanerData sendSearchLocationCleanerData);

    // 4-5 클리너 상세정보 조회
    @GET("cleaner/detail/{cleanerId}")
    Call<SearchCleanerDetailResult> getSearchCleanerDetailResult(@Path("cleanerId") String cleanerId);

    // 4-6 클리너 리뷰작성
    @POST("cleaner/review/{cleanerId}")
    Call<AddCleanerReviewResult> getAddCleanerReviewResult(@Path("cleanerId") String cleanerId, @Body SendAddCleanerReviewData sendAddCleanerData);

    // 5-1 사용자 알람정보 조회
    @GET("/alarm/{userId}")
    Call<ReferAlarmResult> getReferAlarmResult(@Path("userId") String userId);

    // 5-2 알람받기 여부 수정
    @PUT("alarm/{userId}")
    Call<AlarmOnOffResult> getAlarmOnOffResult(@Path("userId") String userId);

    // 6-1 이용내역 조회
    /**API 수정중*/
    @GET("mypage/usage/{userId}")
    Call<MyhistoryResult> getMyHistoryResult(@Path("userId") String userId);

    // 6-2 회원정보 수정(핸드폰번호)
    @PUT("mypage/phone/{userId}")
    Call<ModifyUserPhoneResult> getModifyUserPhoneResult(@Path("userId") String userId, @Header("phone") String phone);

    // 6-3 회원정보 수정(주소)
    @PUT("mypage/address/{userId}")
    Call<ModifyUserAddressResult> getModifyUserAddressResult(@Path("userId") String userId, @Body ModifyUserAddressData modifyUserAddressData);

    // 6-4 회원정보 수정(비밀번호)
    @PUT("mypage/pwd/{userId}")
    Call<ModifyPasswordResult> getModifyPasswordResult(@Path("userId") String userId, @Header("pwd") String pwd);

    // 6-5 회원 탈퇴
    @DELETE("mypage/withdraw/{userId}")
    Call<WithdrawResult> getWithdrawResult(@Path("userId") String userId);

    // 새거
    @POST("lately/{userId}")
    Call<RecentCleanerResult> getRecentCleanerResultNew(@Path("userId") String key, @Body CleanerDateData cleanerDateData);
}