package com.kdp.wanandroidclient.ui.tree;

import com.kdp.wanandroidclient.bean.TreeBean;
import com.kdp.wanandroidclient.net.callback.RxObserver;
import com.kdp.wanandroidclient.ui.mvp.model.impl.TreeModel;
import com.kdp.wanandroidclient.ui.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * author: 康栋普
 * date: 2018/2/26
 */

public class TreePresenter extends BasePresenter<TreeContract.ITreeView> implements TreeContract.ITreePresenter {

    private TreeModel mTreeModel;
    private TreeContract.ITreeView mSystemView;

    public TreePresenter() {
        mTreeModel = new TreeModel();
    }

    @Override
    public void loadTree() {
        mSystemView = getView();
        RxObserver<List<TreeBean>> mTreeRxObserver = new RxObserver<List<TreeBean>>(this) {
            @Override
            protected void onSuccess(List<TreeBean> data) {
                mSystemView.setData(data);
                if (mSystemView.getData().size() == 0) {
                    mSystemView.showEmpty();
                } else {
                    mSystemView.showContent();
                }
            }
            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mSystemView.showFail(errorMsg);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSystemView.showError();
            }
        };
        mTreeModel.getTree(mTreeRxObserver);
        addDisposable(mTreeRxObserver);
    }
}
