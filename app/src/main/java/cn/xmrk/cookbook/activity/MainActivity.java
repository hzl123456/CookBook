package cn.xmrk.cookbook.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cn.xmrk.cookbook.R;
import cn.xmrk.cookbook.fragment.cookbooklist.CookBookFragment;
import cn.xmrk.cookbook.pojo.CookBookKindInfo;
import cn.xmrk.rkandroid.activity.BaseActivity;
import cn.xmrk.rkandroid.net.entity.BaseResultEntity;
import cn.xmrk.rkandroid.net.entity.SubjectPostApi;
import cn.xmrk.rkandroid.net.http.HttpManager;
import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.net.listener.HttpOnNextListener;
import cn.xmrk.rkandroid.task.authority.AuthorityContract;
import cn.xmrk.rkandroid.task.authority.AuthorityPresenter;
import cn.xmrk.rkandroid.utils.CommonUtil;
import rx.Observable;

public class MainActivity extends BaseActivity implements AuthorityContract.View {

    //权限管理的presenter
    private AuthorityContract.Presenter mPresenter;

    private Toolbar toolbar;
    private NavigationView nav_view;
    private DrawerLayout drawer_layout;

    /**
     * 存储各个fragment
     **/
    private List<CookBookFragment> fragments = new ArrayList<>();
    private List<CookBookKindInfo> infos = new ArrayList<>();
    private CookBookFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AuthorityPresenter(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public void initViews() {
        //设置当前的toolbar
        setSupportActionBar(toolbar);
        //为drawer_layout设置toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public void setPresenter(AuthorityContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    /**
     * 这边表示权限管理成功了，可以进行其他的操作了
     **/
    @Override
    public void loadAuthoritySuccess() {
        loadCookBookList();
    }

    /**
     *  这边表示权限管理失败了，对应失败的方法，（这边默认的是在presenter里面给了个弹窗）
     * */
    @Override
    public void loadAuthorityFail() {

    }

    /**
     * 获取菜谱的分类
     **/
    private void loadCookBookList() {
        SubjectPostApi subject = new SubjectPostApi(new HttpOnNextListener() {
            @Override
            public void onNext(BaseResultEntity result) {
                //获取分类信息
                infos = CommonUtil.getGson().fromJson(result.getTngou(), CookBookKindInfo.getListType());
                CookBookKindInfo info = null;
                for (int i = 0; i < infos.size(); i++) {
                    info = infos.get(i);
                    //添加fragment
                    fragments.add(CookBookFragment.newInstance(String.valueOf(info.getId())));
                    //添加menu的item
                    final int position = i;
                    nav_view.getMenu().add(info.getName()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            //跳转fragment
                            setCurrentFragment(fragments.get(position));
                            //关闭drawrlayout
                            drawer_layout.closeDrawer(GravityCompat.START);
                            return true;
                        }
                    });
                }
                setCurrentFragment(fragments.get(0));
            }

        }, this, "加载菜谱分类", true) {
            @Override
            public Observable getObservable(HttpService methods) {
                return methods.getCookBookKinds(String.valueOf(0));
            }
        };
        HttpManager.getInstance().doHttpDeal(subject);
    }

    /**
     * 设置当前显示的fragment
     **/
    private void setCurrentFragment(CookBookFragment fragment) {
        if (currentFragment == null || currentFragment != fragment) {
            FragmentTransaction _ft = getSupportFragmentManager().beginTransaction();
            _ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            _ft.replace(R.id.rv_frame, fragment);
            _ft.commitAllowingStateLoss();
        }
        currentFragment = fragment;
    }
}
