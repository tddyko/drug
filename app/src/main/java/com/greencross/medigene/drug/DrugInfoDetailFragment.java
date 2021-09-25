package com.greencross.medigene.drug;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.WebViewFragment;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.database.DBHelperDataRecipe;
import com.greencross.medigene.database.DBHelperDrugInfo;
import com.greencross.medigene.prescription.PrescriptionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 14..
 * 처방전 관리
 */

public class DrugInfoDetailFragment extends BaseFragment {
    private final String TAG = DrugInfoDetailFragment.class.getSimpleName();
    private String _drugurl;
    private String _drugName;
    private BaseFragment mBaseFragment;
    private TextView drug_title;
    private TextView drug_category;
    private ImageView drug_visual_image;
    private LinearLayout drugAttributesTitle;
    private RelativeLayout drugItem1Group;
    private RelativeLayout drugItem2Group;
    private RelativeLayout drugItem3Group;
    private TextView drugItem1Text;
    private TextView drugItem2Text;
    private TextView drugItem3Text;
    private TextView drugItem1Icon;
    private TextView drugItem2Icon;
    private TextView drugItem3Icon;

    private PrescriptionDialog prescripShowdlg;


    private TextView drug_efficacy_contents;
    private TextView drug_source_contents;

    private Button bnt_prescription_add;
    private Button bnt_favorites_add;
    private Button drug_detailview_button;

    public static Fragment newInstance() {
        DrugInfoDetailFragment fragment = new DrugInfoDetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_details, container, false);
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("검색결과");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drug_title              = (TextView) view.findViewById(R.id.drug_title);
        drug_category           = (TextView) view.findViewById(R.id.drug_category);

        drug_visual_image       = (ImageView) view.findViewById(R.id.drug_visual_image);
        drug_visual_image       = (ImageView) view.findViewById(R.id.drug_visual_image);

        drugAttributesTitle    = (LinearLayout) view.findViewById(R.id.drug_attributes_title);


        drugItem1Group   = (RelativeLayout) view.findViewById(R.id.drug_attributes_item1_group);
        drugItem2Group   = (RelativeLayout) view.findViewById(R.id.drug_attributes_item2_group);
        drugItem3Group   = (RelativeLayout) view.findViewById(R.id.drug_attributes_item3_group);

        drugItem1Text   = (TextView) view.findViewById(R.id.drug_attributes_item1_text);
        drugItem2Text   = (TextView) view.findViewById(R.id.drug_attributes_item2_text);
        drugItem3Text   = (TextView) view.findViewById(R.id.drug_attributes_item3_text);
        drugItem1Icon   = (TextView) view.findViewById(R.id.drug_attributes_item1_icon);
        drugItem2Icon   = (TextView) view.findViewById(R.id.drug_attributes_item2_icon);
        drugItem3Icon   = (TextView) view.findViewById(R.id.drug_attributes_item3_icon);
        drug_efficacy_contents  = (TextView) view.findViewById(R.id.drug_efficacy_contents);
        drug_source_contents    = (TextView) view.findViewById(R.id.drug_source_contents);

        bnt_prescription_add    = (Button) view.findViewById(R.id.bnt_prescription_add);
        bnt_favorites_add       = (Button) view.findViewById(R.id.bnt_favorites_add);
        drug_detailview_button  = (Button) view.findViewById(R.id.drug_detailview_button);

        if (getArguments() != null) {
            showProgress();
            String idx = getArguments().getString(DRUGINFO_IDX);
            getData(idx);
        }

        bnt_prescription_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drugIdx = "";
            if (getArguments() != null) {
                drugIdx = getArguments().getString(DRUGINFO_IDX);
            }

            // 처방전 추가 다이얼로그
            DBHelper helper = new DBHelper(getContext());
            DBHelperDataRecipe db = helper.getmDataRecipe();
            List<DBHelperDataRecipe.RecipeData> listData = db.getResult();
            prescripShowdlg = new PrescriptionDialog(DrugInfoDetailFragment.this, drugIdx);
            prescripShowdlg.setData(listData);
            }
        });


        bnt_favorites_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                DBHelper helper = new DBHelper(getContext());
                DBHelperDrugInfo db = helper.getDrugInfoDb();
                int idx = Integer.parseInt(getArguments().getString(DRUGINFO_IDX));
                db.setFavoritesDb(idx);
                Toast.makeText(getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });

        drug_detailview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                Bundle bundle = new Bundle();
                bundle.putString(PARAMS_ACTION_URL, _drugurl);
                bundle.putString(CommonActionBar.ACTION_BAR_TITLE, _drugName);
                movePage(WebViewFragment.newInstance(), bundle);
            }
        });


    }

    private void getData(String idx) {
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo db = helper.getDrugInfoDb();
        DBHelperDrugInfo.DrugData drugData = db.getResultDetail(idx);

        if (drugData != null){
            String drugtitle = drugData.drug_title;
            String drugcategory = drugData.drug_category;
            String drugimg = drugData.drug_img;
            String drugeffect = drugData.drug_effect;
            String drugsource = drugData.drug_source;
            _drugurl = drugData.drug_url;
            _drugName = drugData.drug_title;

            drug_title.setText(drugtitle);
            drug_category.setText(drugcategory);

            try {
                if (!TextUtils.isEmpty(drugData.drug_img)) {
                    String uri = "@drawable/" + drugimg;  // where myresource (without the extension) is the file
                    int imageResource = getResources().getIdentifier(uri, null, getContext().getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    drug_visual_image.setBackground(res);
                }else{
                    drug_visual_image.setVisibility(View.GONE);
                }
            }catch (Exception e){
                drug_visual_image.setVisibility(View.GONE);
            }
            drug_efficacy_contents.setText(drugeffect);
            drug_source_contents.setText(drugsource);
        }

        getItemsData(idx);
    }


    private void getItemsData(String idx) {
        DBHelper helper = new DBHelper(getContext());
        DBHelperDrugInfo db = helper.getDrugInfoDb();
        List<DBHelperDrugInfo.DrugItem> items = db.getItemsResult(idx);

        for (int i=0; i < items.size(); i++){
            DBHelperDrugInfo.DrugItem data = items.get(i);
            if (i==0){
                drugAttributesTitle.setVisibility(View.VISIBLE);
                drugItem1Group.setVisibility(View.VISIBLE);
                drugItem1Text.setText(data.drug_name.toString());
                if (data.drug_value.equals("0")){
                    drugItem1Icon.setText("이상반응 주의");
                    drugItem1Icon.setBackgroundColor(getResources().getColor(R.color.colorItemMain));
                }else if (data.drug_value.equals("1")){
                    drugItem1Icon.setText("치료효과 감소");
                    drugItem1Icon.setBackgroundColor(getResources().getColor(R.color.colorItemOrange));
                }else if (data.drug_value.equals("2")){
                    drugItem1Icon.setText("표준");
                    drugItem1Icon.setBackgroundColor(getResources().getColor(R.color.colorItemGreen));
                }else if (data.drug_value.equals("3")){
                    drugItem1Icon.setText("검사항목에 없음");
                    drugItem1Icon.setBackgroundColor(getResources().getColor(R.color.colorItemGray));
                }
            }else if (i==1){
                drugItem2Group.setVisibility(View.VISIBLE);
                drugItem2Text.setText(data.drug_name.toString());
                if (data.drug_value.equals("0")){
                    drugItem2Icon.setText("이상반응 주의");
                    drugItem2Icon.setBackgroundColor(getResources().getColor(R.color.colorItemMain));
                }else if (data.drug_value.equals("1")){
                    drugItem2Icon.setText("치료효과 감소");
                    drugItem2Icon.setBackgroundColor(getResources().getColor(R.color.colorItemOrange));
                }else if (data.drug_value.equals("2")){
                    drugItem2Icon.setText("표준");
                    drugItem2Icon.setBackgroundColor(getResources().getColor(R.color.colorItemGreen));
                }else if (data.drug_value.equals("3")){
                    drugItem2Icon.setText("검사항목에 없음");
                    drugItem2Icon.setBackgroundColor(getResources().getColor(R.color.colorItemGray));
                }
            }else if (i==2){
                drugItem3Group.setVisibility(View.VISIBLE);
                drugItem3Text.setText(data.drug_name.toString());
                if (data.drug_value.equals("0")){
                    drugItem3Icon.setText("이상반응 주의");
                    drugItem3Icon.setBackgroundColor(getResources().getColor(R.color.colorItemMain));
                }else if (data.drug_value.equals("1")){
                    drugItem3Icon.setText("치료효과 감소");
                    drugItem3Icon.setBackgroundColor(getResources().getColor(R.color.colorItemOrange));
                }else if (data.drug_value.equals("2")){
                    drugItem3Icon.setText("표준");
                    drugItem3Icon.setBackgroundColor(getResources().getColor(R.color.colorItemGreen));
                }else if (data.drug_value.equals("3")){
                    drugItem3Icon.setText("검사항목에 없음");
                    drugItem3Icon.setBackgroundColor(getResources().getColor(R.color.colorItemGray));
                }
            }
        }
        hideProgress();
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        BaseFragment mFragment;

        public RecyclerAdapter(BaseFragment fragment) {
            mFragment = fragment;
        }

        List<DBHelperDrugInfo.DrugData> drugData = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_drugsearch_list_item, parent, false);
            return new ViewHolder(itemView);
        }

        public void setData(List<DBHelperDrugInfo.DrugData> dataList) {
            drugData.clear();
            drugData.addAll(dataList);

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final DBHelperDrugInfo.DrugData data = drugData.get(position);
            holder.txtTitle.setText(data.drug_title);
            holder.rowitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(DRUGINFO_IDX, data.idx);
//                    bundle.putString(CommonActionBar.ACTION_BAR_TITLE, "건강정보");
                    movePage(DrugInfoDetailFragment.newInstance(), bundle);


//                    DBHelper helper = new DBHelper(getContext());
//                    DBHelperDataRecipe db = helper.getmDataRecipe();
//                    db.DeleteDb(data.idx);
//                    getData();
                }
            });
        }

        @Override
        public int getItemCount() {
            return drugData == null ? 0 : drugData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle;
            LinearLayout rowitem;

            public ViewHolder(View itemView) {
                super(itemView);
                txtTitle = (TextView) itemView.findViewById(R.id.drug_title_textview);
                rowitem= (LinearLayout) itemView.findViewById(R.id.drug_rowitem);
//                deleteBtn = (Button) itemView.findViewById(R.id.prescript_delete_btn);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(prescripShowdlg != null){
            prescripShowdlg.getSetData();
        }
    }

}
