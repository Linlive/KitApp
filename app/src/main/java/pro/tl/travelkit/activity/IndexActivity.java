package pro.tl.travelkit.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import pro.tl.travelkit.R;
import pro.tl.travelkit.fragment.AppIndexAbMeFrag;
import pro.tl.travelkit.fragment.AppIndexFragment;

public class IndexActivity extends Activity implements View.OnClickListener {

	public static int mTextColor = Color.parseColor("#f42f94df");

	private FragmentManager fragmentManager;

	private AppIndexFragment homeFragment;
	private AppIndexAbMeFrag aboutFragment;

	private View homeLayout;
	private View aboutMeLayout;

	private ImageView homeImage;

	private TextView homeText;

	private ImageView aboutMeImage;

	private TextView aboutMeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);

		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		setTabSelection(0);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		homeLayout = findViewById(R.id.index_home_layout);
		aboutMeLayout = findViewById(R.id.index_about_me_layout);

		homeImage = (ImageView) findViewById(R.id.index_home_image);
		homeText = (TextView) findViewById(R.id.index_home_text);

		aboutMeImage = (ImageView) findViewById(R.id.index_about_me_image);
		aboutMeText = (TextView) findViewById(R.id.index_about_me_text);

		homeLayout.setOnClickListener(this);
		aboutMeLayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.index_home_layout:
				// 当点击了消息tab时，选中第1个tab
				setTabSelection(0);
				break;
			case R.id.index_about_me_layout:
				// 当点击了联系人tab时，选中第2个tab
				setTabSelection(1);
				break;
			default:
				break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 *
	 * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
			case 0:
				// 当点击了消息tab时，改变控件的图片和文字颜色
				homeImage.setImageResource(R.drawable.home_selected);
				homeText.setTextColor(mTextColor);
				if (homeFragment == null) {
					// 如果MessageFragment为空，则创建一个并添加到界面上
					homeFragment = new AppIndexFragment();
					transaction.add(R.id.container, homeFragment);
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(homeFragment);
				}
				break;
			case 1:
				aboutMeImage.setImageResource(R.drawable.user_selected);
				aboutMeText.setTextColor(mTextColor);
				if (aboutFragment == null) {
					// 如果MessageFragment为空，则创建一个并添加到界面上
					aboutFragment = new AppIndexAbMeFrag();
					transaction.add(R.id.container, aboutFragment);
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(aboutFragment);
				}
				break;
			case 2:
				// 当点击了动态tab时，改变控件的图片和文字颜色

				break;
			case 3:
			default:
				// 当点击了设置tab时，改变控件的图片和文字颜色

				break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		homeImage.setImageResource(R.drawable.home_unselected);
		homeText.setTextColor(Color.parseColor("#82858b"));
		aboutMeImage.setImageResource(R.drawable.user_unselected);
		aboutMeText.setTextColor(Color.parseColor("#82858b"));
//		contactsImage.setImageResource(R.drawable.contacts_unselected);
//		contactsText.setTextColor(Color.parseColor("#82858b"));
//		newsImage.setImageResource(R.drawable.news_unselected);
//		newsText.setTextColor(Color.parseColor("#82858b"));
//		settingImage.setImageResource(R.drawable.setting_unselected);
//		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction 用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if(aboutFragment != null){
			transaction.hide(aboutFragment);
		}
//		if (contactsFragment != null) {
//			transaction.hide(contactsFragment);
//		}
//		if (newsFragment != null) {
//			transaction.hide(newsFragment);
//		}
//		if (settingFragment != null) {
//			transaction.hide(settingFragment);
//		}
	}

}
