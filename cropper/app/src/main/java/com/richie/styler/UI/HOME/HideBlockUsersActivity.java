package com.richie.styler.UI.HOME;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Adapter.RecyclerSearchAdapter;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.DETAIL.DetailActivity;
import com.richie.styler.UI.Models.Block.ModelBlocked;
import com.richie.styler.UI.Models.ImagegridModel;
import com.richie.styler.UI.Models.StylerSearch.ModelStylerSearch;
import com.richie.styler.UI.Models.StylerSearch.Result;
import com.richie.styler.UI.Models.StylerSearch.Styler;
import com.richie.styler.UTILS.BlockinterfaceCallback;
import com.richie.styler.UTILS.BlocktodetailCallback;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 18-04-2017.
 */

public class HideBlockUsersActivity extends BaseActivity {
    ImageView back;
    RecyclerView search;
    EditText searchusers;
    String username="";
    int pos;
    List<Styler>list=new ArrayList<>();
    RecyclerSearchAdapter adapter;
    public ArrayList<ImagegridModel> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hide_block_activity);
        initui();
        list1=new ArrayList<>();
        setrecyclerview();
        setAdapter();
        if (isNetworkAvailable())
        {
            getwebservice();
        }
        setui();

    }

    private void setui() {
        adapter.setOnBlockOnlistener(new BlockinterfaceCallback() {
            @Override
            public void setBlock(int position, int blockstatus) {
                String blockedid=list.get(position).getUserId();
                getwebservice1(blockstatus,blockedid);
            }
        });
        adapter.setonBlockdetaillistener(new BlocktodetailCallback() {
            @Override
            public void setcallback(int position) {
                String userid=list.get(position).getUserId();
                Intent intent=new Intent(HideBlockUsersActivity.this, DetailActivity.class);
                intent.putExtra("user_id",userid);
                startActivity(intent);
            }
        });
//        ItemClickSupport.addTo(search).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                boolean selected=list1.get(position).isSelected();
//                if (selected)
//                {
//                    int value=0;
//                    pos=position;
//                    String blockedid=list.get(position).getUserId();
//                    list1.get(position).setSelected(false);
//                    adapter.notifyDataSetChanged();
//                    getwebservice1(value,blockedid);
//                }
//                else {
//                    int value=1;
//                    String blockedid=list.get(position).getUserId();
//                    pos=position;
//                    list1.get(position).setSelected(true);
//                    adapter.notifyDataSetChanged();
//                    getwebservice1(value,blockedid);
//
//                }
//
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchusers.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    username=searchusers.getText().toString().trim();
                    getwebservice();
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    private void getwebservice1(final int value, final String blockedid) {
        final HashMap<String, String> params = new HashMap<>();
        final String userid=getPref(Constants.USER_ID);
        showProgress();
        params.put("blocked_by",userid);
        params.put("blocked_user_id",blockedid);
        params.put("block_status",String.valueOf(value));
        APIService service=ServiceGenerator.createService(APIService.class,HideBlockUsersActivity.this);
        Call<ModelBlocked>call=service.getblocked(params);
        call.enqueue(new Callback<ModelBlocked>() {
            @Override
            public void onResponse(Call<ModelBlocked> call, Response<ModelBlocked> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelBlocked blocked=response.body();
                        int value1=blocked.getResult().getValue();
                        String msg=blocked.getResult().getMessage();
                        if (value1==1)
                        {
                            showToast(msg,true);
                            if (value==1)
                            {
                                list1.get(pos).setSelected(false);
                            }
                            else
                            {
                                list1.get(pos).setSelected(true);
                            }
//                            getwebservice();


                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<ModelBlocked> call, Throwable t) {
            hideProgress();
            }
        });

    }

    private void setAdapter() {
        adapter=new RecyclerSearchAdapter(HideBlockUsersActivity.this,list1);
        search.setAdapter(adapter);
    }

    private void setrecyclerview() {
        search.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(HideBlockUsersActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        search.setLayoutManager(llm);
    }

    private void getwebservice() {
        HashMap<String, String> params = new HashMap<>();
        final String userid=getPref(Constants.USER_ID);
        showProgress();
        if (!username.isEmpty())
        {
            params.put("profilename",username);
        }
        params.put("userid",userid);
        params.put("limit","40");
        APIService service = ServiceGenerator.createService(APIService.class,HideBlockUsersActivity.this);
        Call<ModelStylerSearch>call=service.getsearch(params);
        call.enqueue(new Callback<ModelStylerSearch>() {
            @Override
            public void onResponse(Call<ModelStylerSearch> call, Response<ModelStylerSearch> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelStylerSearch model=response.body();
                        Result result=model.getResult();
                        list1.clear();
                        list=result.getStylers();
                        if (list.size()>0) {
                            for (int i = 0; i < list.size(); i++) {
                                String image = list.get(i).getUserPhoto();
                                String username = list.get(i).getUsername();
                                String blockstatus=list.get(i).getBlockStatus();
                                int block=Integer.valueOf(blockstatus);
                                String userid=list.get(i).getUserId();

                                ImagegridModel model1 = new ImagegridModel();
                                if (block==1)
                                {
                                    model1.setSelected(true);
                                }
                                else {
                                    model1.setSelected(false);
                                }
                                model1.setTribeName(username);
                                model1.setTribeImage(image);
                                list1.add(model1);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            adapter.notifyDataSetChanged();
                            showToast("No Result Found!",false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelStylerSearch> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private void initui() {
        back= (ImageView) findViewById(R.id.back);
        search= (RecyclerView) findViewById(R.id.recycleview_search);
        searchusers= (EditText) findViewById(R.id.edittext_search_users);
    }


}
