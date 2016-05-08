package pro.tl.travelkit.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.tl.travelkit.R;

/**
 * Created by Administrator on 2016/4/24.
 */
public class AppIndexAbMeFrag extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		return inflater.inflate(R.layout.frag_index_about_me, container, false);
	}
}
