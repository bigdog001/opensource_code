package com.btten.toolkit.testnet;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.widget.ListView;
import com.btten.btpulldownlistview.R;
import com.btten.toolkit.pulldownlistview.LoadingHelper;
import com.btten.toolkit.pulldownlistview.LoadingListener;
import com.btten.toolkit.pulldownlistview.PullDownRefreshView;
import com.btten.toolkit.pulldownlistview.PullDownRefreshView.pullToRefreshListener;
import com.btten.toolkit.pulldownlistview.PullDownRefreshView.onLoadMoreListener;

public class MainActivity extends Activity implements LoadingListener {

	PullDownRefreshView refreshView;
	LoadingHelper loadingHelper;
	public int pageindex = 1;
	public int DataSizePerPage = 10;

	ListView listView;
	public TestAdapter adapter;
	TestDataLoader testData = new TestDataLoader();
	ArrayList<String> items = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		// 可自定义的网络加载挡板和数据为空的view
		loadingHelper = new LoadingHelper(this, findViewById(R.id.loading_prompt_linear), findViewById(R.id.loading_empty_prompt_linear));
		loadingHelper.ShowLoading();
		loadingHelper.SetListener(this);

		// 下拉刷新上拉加载更多控件
		refreshView = (PullDownRefreshView) findViewById(R.id.pulldown_refreshview);
		listView = (ListView) refreshView.getChildAt(1);
		adapter = new TestAdapter(this);
		listView.setAdapter(adapter);
		refreshView.setOnRefreshListener(new pullToRefreshListener() {
			@Override
			public void onRefresh() {
				refreshView.post(new Runnable() {
					@Override
					public void run() {
						refreshView.setOnLoadState(false, true); // 设置加载状态,参数为isFinished和isRefreshing
						pageindex = 1;
						doLoadMore();
					}
				});
			}
		}, 0);// 这里id只是一个标志,用以区分不同页面上次下拉刷新的时间
		refreshView.setOnLoadMoreListener(new onLoadMoreListener() {
			@Override
			public void onLoadMore() {
				refreshView.setOnLoadState(false, false);
				pageindex++;
				doLoadMore();
			}
		});
		doLoadMore();
	}

	/**
	 * 测试模拟加载更多数据
	 */
	public void doLoadMore() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				onSuccess();
			}
		}, 1500);
	}

	/**
	 * 数据加载成功
	 */
	public void onSuccess() {
		loadingHelper.HideLoading(8);
		items = testData.getCurrentPageItems(pageindex);
		if (refreshView.getRefreshState()) {
			if (adapter != null)
				adapter.clear();
			refreshView.finishRefreshing();// 刷新完成隐藏下拉headview
		}
		refreshView.setOnLoadState(false, false);
		refreshView.initListFootView(adapter); // 初始化加载更多的footview
		adapter.addItems(items);
		if ((items == null || items.size() == 0) && pageindex == 1) {
			loadingHelper.ShowEmptyData();
			refreshView.removeListFootView(); // 移除加载更多的footview
			return;
		}
		if (items == null || items.size() < DataSizePerPage) {
			Toast.makeText(this, R.string.loading_data_finished, Toast.LENGTH_SHORT).show();
			refreshView.removeListFootView();
		}
	}

	/**
	 * 数据加载失败
	 */
	public void onFail() {
		loadingHelper.ShowError("显示网络出错信息!");
	}

	@Override
	public void OnRetryClick() {
		loadingHelper.ShowLoading();
		if (adapter != null)
			adapter.clear();
		refreshView.setOnLoadState(false, false);
		pageindex = 1;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				loadingHelper.HideLoading(8);
				onSuccess();
			}
		}, 1500);
	}

}