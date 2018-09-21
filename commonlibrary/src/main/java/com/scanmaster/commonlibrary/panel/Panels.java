package com.scanmaster.commonlibrary.panel;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * author:  zhouchaoxiang
 * date:    2018/9/20
 * explain:
 */
public class Panels {
    private List<Panel> mPanelList;

    public Panels() {
        mPanelList = new ArrayList<>();
    }

    public void addPanel(Panel... panels) {
        for (Panel panel : panels)
            if (panel != null)
                this.mPanelList.add(panel);
    }

    public Panel getPanel(int index) {
        return mPanelList.get(index);
    }

    public List<Panel> getPanels() {
        return mPanelList;
    }

    public void removePanel(Panel panel) {
        mPanelList.remove(panel);
    }

    public void removePanel(int index) {
        mPanelList.remove(index);
    }

    public void clear() {
        mPanelList.clear();
    }

    public void onCreate(Bundle savedInstanceState) {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onCreate(savedInstanceState);
            }
        }
    }

    public void onCreateView() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onCreateView();
            }
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onActivityCreated(savedInstanceState);
            }
        }
    }

    public void onStart() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onStart();
            }
        }
    }

    public void onRestart() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onRestart();
            }
        }
    }

    public void onResume() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onResume();
            }
        }
    }

    public void onPause() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onPause();
            }
        }
    }

    public void onStop() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onStop();
            }
        }
    }

    public void onDestroyView() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onDestroyView();
            }
        }
    }

    public void onDestroy() {
        if (null != mPanelList && mPanelList.size() > 0) {
            for (Panel panel : mPanelList) {
                panel.onDestroy();
            }
        }
        clear();
    }

}
