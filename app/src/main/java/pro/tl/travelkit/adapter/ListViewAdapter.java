package pro.tl.travelkit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import pro.tl.travelkit.R;

/**
 * 测试listView
 * Created by Administrator on 2016/4/18.
 */
public class ListViewAdapter extends BaseAdapter {

	private static List<HashMap<String, Object>> mDataList;

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private DisplayImageOptions options;
	public ListViewAdapter(Context context, List<HashMap<String, Object>> dataList) {
		this.mContext = context;
		mDataList = dataList;
		mLayoutInflater = LayoutInflater.from(context);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.build();
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder vh;
		View view = convertView;

		if(convertView == null){
			vh = new ViewHolder();
			view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
			vh.imageView = (ImageView) view.findViewById(R.id.image_detail);
			vh.textViewTitle = (TextView) view.findViewById(R.id.text_title);
			vh.textViewDesc = (TextView) view.findViewById(R.id.text_describe);
			view.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage((String)mDataList.get(position).get("imageUrl"), vh.imageView, options, new AnimateFirstDisplayListener());
		vh.textViewDesc.setText(mDataList.get(position).get("describe").toString());
		vh.textViewTitle.setText(mDataList.get(position).get("title").toString());

		return view;
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	private class ViewHolder{
		ImageView imageView;
		TextView textViewTitle;
		TextView textViewDesc;
	}
}
