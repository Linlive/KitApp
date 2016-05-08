package pro.tl.travelkit.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;
import java.util.LinkedList;

import pro.tl.travelkit.Constants;
import pro.tl.travelkit.R;
import pro.tl.travelkit.adapter.ListViewAdapter;
import pro.tl.travelkit.view.custom.RefreshListView;

/**
 * app 主页
 * Created by Administrator on 2016/4/24.
 */
public class AppIndexFragment extends Fragment implements RefreshListView.IRefreshListener {


	private static LinkedList<HashMap<String, Object>> mData = new LinkedList<>();

	private Context mContext;
	private PullToRefreshListView mPullToRefreshListView;
	private ListViewAdapter mAdapter;

	private Button button;
	private String[] urls = {
			"http://pic36.nipic.com/20131217/6704106_233034463381_2.jpg",
			"http://pic32.nipic.com/20130829/12906030_124355855000_2.png",
			"http://banbao.chazidian.com/uploadfile/2016-01-25/s145368924044608.jpg"
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle("kit");
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		mContext = getActivity();
		View view = inflater.inflate(R.layout.frag_index_layout, container, false);
		initView(view);
		setListener();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setData();
		mPullToRefreshListView.setAdapter(mAdapter);

		mAdapter.notifyDataSetChanged();
		mAdapter.notifyDataSetInvalidated();
		mPullToRefreshListView.onRefreshComplete();
	}

	/**
	 *
	 * @param v 布局视图
	 */
	private void initView(View v) {
		button = (Button) v.findViewById(R.id.testButton);
		mPullToRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);
		mAdapter = new ListViewAdapter(mContext, mData);
	}

	/**
	 *
	 */
	private void setListener() {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("===========");
			}
		});
		mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh( PullToRefreshBase<ListView> refreshView) {
				Toast.makeText(mContext, "onPullDownToRefresh", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}
			@Override
			public void onPullUpToRefresh( PullToRefreshBase<ListView> refreshView) {
				Toast.makeText(mContext, "onPullUpToRefresh", Toast.LENGTH_SHORT).show();
				new DataLoadTask().execute();
			}
		});
	}
	private void setData() {
		HashMap map;
		for(int i = 1; i < 4; i++){
			map = new HashMap();
			map.put("imageUrl", urls[i-1]);
			map.put("describe", "描述" + i);
			map.put("title", "标题" + i);
			mData.add(map);
		}
	}

	public void onRefresh() {
		new GetDataTask().execute();
	}
	@Override
	public void onLoadMore() {
		new DataLoadTask().execute();
	}
	//模拟网络加载数据的   异步请求类
	//
	private class GetDataTask extends AsyncTask<String, Void, String[]> {

		//子线程请求数据
		@Override
		protected String[] doInBackground(String... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return Constants.IMAGES;
		}

		//主线程更新UI
		@Override
		protected void onPostExecute(String[] result) {

			//向RefreshListView Item 添加一行数据  并刷新ListView
			//mListItems.addLast("Added after refresh...");
			HashMap map = new HashMap();
			map.put("imageUrl", result[10]);
			map.put("describe", "描述更新后的一个");
			map.put("title", "标题 更新后的 一个");
			mData.addFirst(map);
			mAdapter.notifyDataSetChanged();

			//通知RefreshListView 我们已经更新完成
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private class DataLoadTask extends AsyncTask<String, Void, String[]> {

		//子线程请求数据
		@Override
		protected String[] doInBackground(String... params) {
			// Simulates a background job.
			String[] res = null;
			try {
				Thread.sleep(1000);
				res = new String[]{"http://img5.imgtn.bdimg.com/it/u=1017606633,46849118&fm=11&gp=0.jpg"};
			} catch (InterruptedException e) {
			}
			return res;
		}

		//主线程更新UI
		@Override
		protected void onPostExecute(String[] result) {

			//向RefreshListView Item 添加一行数据  并刷新ListView
			//mListItems.addLast("Added after refresh...");
			HashMap map = new HashMap();
			map.put("imageUrl", result[0]);
			map.put("describe", "描述更新后的一个");
			map.put("title", "标题 更新后的 一个");
			mData.addLast(map);
			mAdapter.notifyDataSetChanged();

			//通知RefreshListView 我们已经更新完成
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onLoadMoreComplete();

			super.onPostExecute(result);
		}
	}
}
