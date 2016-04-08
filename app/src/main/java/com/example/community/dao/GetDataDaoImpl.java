package com.example.community.dao;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class GetDataDaoImpl implements GetDataDao {

	@Override
	public void getData(final RequestVo requestVo,
						final LoadCallBack loadCallBack) {
		HttpUtils http = new HttpUtils();
//		final SASAC_Application myApplication = (SASAC_Application) requestVo
//				.getContext().getApplicationContext();
//		http.configCookieStore(myApplication.getPreferencesCookieStore());
		http.send(requestVo.getHttpMethod(), requestVo.getRequestUrl(),
				requestVo.getRequestParams(), new RequestCallBack<String>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onLoading(long total, long current,
										  boolean isUploading) {
						loadCallBack.onLoading(0);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						System.out.println("数据："+responseInfo.result);
						loadCallBack.onLoadSuccess(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						loadCallBack.onLoadFail();
					}
				});

	}

}
