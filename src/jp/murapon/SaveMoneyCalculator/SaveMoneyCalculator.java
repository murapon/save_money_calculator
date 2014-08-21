package jp.murapon.SaveMoneyCalculator;

import jp.co.nobot.libAdMaker.libAdMaker;

import jp.murapon.SaveMoneyCalculator.DiscountCalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.TabActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class SaveMoneyCalculator extends TabActivity implements View.OnClickListener{

    // 割引
    DiscountCalc discount;

    // ○○につき
    TextView perWeightPerPrice;
    EditText perPrice1;
    EditText perPrice2;
    EditText perPrice3;
    EditText perWeight1;
    EditText perWeight2;
    EditText perWeight3;
    TextView perWeightPrice1;
    TextView perWeightPrice2;
    TextView perWeightPrice3;
    Spinner  standardSpinner;
    private Button button_per_calc;
    private Button button_per_reset;

    // ポイント
    EditText pointPrice;
    EditText pointRate;
    TextView pointGetPoint;
    TextView pointExcludePoint;
    private Button button_point_calc;
    private Button button_point_reset;

    // 消費税
    EditText taxPrice;
    EditText taxFlg;
    EditText taxRate;
    private Button button_tax_5per_calc;
    private Button button_tax_8per_calc;
    private Button button_tax_10per_calc;
    private Button button_tax_reset;
    TextView taxEstablishedPrice;
    TextView taxCosumptionTax;
    TextView taxTotalPrice;

    private static String[] sTabId = {"Tab1","Tab2","Tab3","Tab4"};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        // TabHostオブジェクト取得 
        TabHost tabs = getTabHost();
  
        // Tab1 設定 
        TabSpec tab1 = tabs.newTabSpec(sTabId[0]);
        tab1.setIndicator(getString(R.string.tab_discount));
        tab1.setContent(R.id.discount);
        tabs.addTab(tab1);
        // Tab2 設定 
        TabSpec tab2 = tabs.newTabSpec(sTabId[1]);
        tab2.setIndicator(getString(R.string.tab_weight));
        tab2.setContent(R.id.per);
        tabs.addTab(tab2);
        // Tab3 設定 
        TabSpec tab3 = tabs.newTabSpec(sTabId[2]);
        tab3.setIndicator(getString(R.string.tab_point));
        tab3.setContent(R.id.point);
        tabs.addTab(tab3);
        // Tab4 設定 
        TabSpec tab4 = tabs.newTabSpec(sTabId[3]);
        tab4.setIndicator(getString(R.string.tab_consumption_tax));
        tab4.setContent(R.id.consumption_tax);
        tabs.addTab(tab4);
        // 初期表示設定 
        tabs.setCurrentTab(0);

        // 割引 ---- START
        discount = new DiscountCalc(this);
        // 割引 ---- END

        // ○○につき ---- START
        perWeightPerPrice = (TextView)findViewById(R.id.PerWeightPerPrice);
        String per_weight_per_price = getString(R.string.per_weight_per_price);
        per_weight_per_price = per_weight_per_price.replaceAll("%per","100");
        perWeightPerPrice.setText(per_weight_per_price);
        // 金額
        perPrice1 = (EditText)findViewById(R.id.PerPrice1);
        perPrice1.setText("");
        perPrice2 = (EditText)findViewById(R.id.PerPrice2);
        perPrice2.setText("");
        perPrice3 = (EditText)findViewById(R.id.PerPrice3);
        perPrice3.setText("");
        // 内容量
        perWeight1 = (EditText)findViewById(R.id.PerWeight1);
        perWeight1.setText("");
        perWeight2 = (EditText)findViewById(R.id.PerWeight2);
        perWeight2.setText("");
        perWeight3 = (EditText)findViewById(R.id.PerWeight3);
        perWeight3.setText("");
        // 基準種別
        standardSpinner = (Spinner)findViewById(R.id.StandardKind);
        ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(this,
                R.array.standard_array, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        standardSpinner.setAdapter(adapter_2);
        // 計算するボタン
        button_per_calc = (Button)findViewById(R.id.ButtonPerCalc);
        button_per_calc.setOnClickListener(this);
        // リセットボタン
        button_per_reset = (Button)findViewById(R.id.ButtonPerReset);
        button_per_reset.setOnClickListener(this);
        // ○○につき ---- END

        // ポイント ---- START
        pointPrice = (EditText)findViewById(R.id.PointPrice);
        pointPrice.setText("");
        pointRate = (EditText)findViewById(R.id.PointRate);
        pointRate.setText("");
        // 計算するボタン
        button_point_calc = (Button)findViewById(R.id.ButtonPointCalc);
        button_point_calc.setOnClickListener(this);
        // リセットボタン
        button_point_reset = (Button)findViewById(R.id.ButtonPointReset);
        button_point_reset.setOnClickListener(this);
        // ポイント ---- END
        
        // 消費税 ---- START
        taxPrice = (EditText)findViewById(R.id.TaxPrice);
        taxPrice.setText("");
        // 計算するボタン
        button_tax_5per_calc = (Button)findViewById(R.id.ButtonTax5PerCalc);
        button_tax_5per_calc.setOnClickListener(this);
        button_tax_8per_calc = (Button)findViewById(R.id.ButtonTax8PerCalc);
        button_tax_8per_calc.setOnClickListener(this);
        button_tax_10per_calc = (Button)findViewById(R.id.ButtonTax10PerCalc);
        button_tax_10per_calc.setOnClickListener(this);
        // リセットボタン
        button_tax_reset = (Button)findViewById(R.id.ButtonTaxReset);
        button_tax_reset.setOnClickListener(this);
        // 消費税 ---- END
        
        // AdMaker
        libAdMaker AdMaker = (libAdMaker)findViewById(R.id.admakerview);
        AdMaker.setActivity(this);
        AdMaker.siteId = "1228";
        AdMaker.zoneId = "2826";
        AdMaker.setUrl("http://images.ad-maker.info/apps/73hri8witgvf.html");
        AdMaker.start();

        //AdMob
        AdView adView = new AdView(this, AdSize.BANNER, "a14dc027a24243d"); //広告Viewを作成   
        LinearLayout layout = (LinearLayout)findViewById(R.id.admob); //LinearLayoutを探す   
        layout.addView(adView); //広告Viewを追加 
        AdRequest request = new AdRequest();       //広告のリクエストを作成
        request.setGender(AdRequest.Gender.FEMALE);
        request.setTesting(true);          //Debug Mode　リリース時は外す！ 
        adView.loadAd(request);           //リクエスト発行
    }

    public void onClick(View view){

        // ソフトキーボードを落とす
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        // ロケールを取得する
        String locale = getString(R.string.locale);

        // 割引計算
        if(view==discount.getButtonDiscountCalc()){
            discount.calc(this, locale);
        }
        // 割引計算(リセット)
        if(view==discount.getButtonDiscountReset()){
            discount.reset(this);
        }

        // ○○につき計算
        if(view==button_per_calc){
            perPrice1 = (EditText)findViewById(R.id.PerPrice1);   // 金額
            perPrice2 = (EditText)findViewById(R.id.PerPrice2);
            perPrice3 = (EditText)findViewById(R.id.PerPrice3);
            perWeight1 = (EditText)findViewById(R.id.PerWeight1); // 内容量
            perWeight2 = (EditText)findViewById(R.id.PerWeight2);
            perWeight3 = (EditText)findViewById(R.id.PerWeight3);
            int standard_id = standardSpinner.getSelectedItemPosition();
            if (perPrice1.getText().toString().equals("") && perPrice2.getText().toString().equals("") && perPrice3.getText().toString().equals("") &&
                perWeight1.getText().toString().equals("") && perWeight2.getText().toString().equals("") && perWeight3.getText().toString().equals("")){
                showDialog(this, "", getString(R.string.per_error));
                return;
            }
            switch (standard_id) {
            case 0:
                // 100の時
                setPerPrice(100);
                break;
            case 1:
                // 1の時
                setPerPrice(1);
                break;
            case 2:
                // 10の時
                setPerPrice(10);
                break;
            case 3:
                // 1000の時
                setPerPrice(1000);
                break;
            default:
                break;
            }
        }
        // ○○につき計算(リセット)
        if(view==button_per_reset){
            // 金額
            perPrice1 = (EditText)findViewById(R.id.PerPrice1);
            perPrice1.setText("");
            perPrice2 = (EditText)findViewById(R.id.PerPrice2);
            perPrice2.setText("");
            perPrice3 = (EditText)findViewById(R.id.PerPrice3);
            perPrice3.setText("");
            perWeight1 = (EditText)findViewById(R.id.PerWeight1);
            perWeight1.setText("");
            perWeight2 = (EditText)findViewById(R.id.PerWeight2);
            perWeight2.setText("");
            perWeight3 = (EditText)findViewById(R.id.PerWeight3);
            perWeight3.setText("");
            perWeightPrice1 = (TextView)findViewById(R.id.PerWeightPrice1);
            perWeightPrice1.setText("");
            perWeightPrice2 = (TextView)findViewById(R.id.PerWeightPrice2);
            perWeightPrice2.setText("");
            perWeightPrice3 = (TextView)findViewById(R.id.PerWeightPrice3);
            perWeightPrice3.setText("");
        }
        // ポイント計算
        if(view==button_point_calc){
            pointPrice = (EditText)findViewById(R.id.PointPrice);   // 金額
            pointRate = (EditText)findViewById(R.id.PointRate);     // ポイント率
            if (pointPrice.getText().toString().equals("") || pointRate.getText().toString().equals("")){
                    showDialog(this, "", getString(R.string.point_error));
                    return;
            }
            int get_point = (int)Math.floor(Double.valueOf(pointPrice.getText().toString()) * Integer.valueOf(pointRate.getText().toString()) * 0.01);
            pointGetPoint = (TextView)findViewById(R.id.PointGetPoint);
            pointGetPoint.setText(Integer.toString(get_point));
            int exclude_point = (int)Math.floor(Double.valueOf(pointPrice.getText().toString())) - get_point;
//            BigDecimal bd = new BigDecimal(exclude_point);
//            exclude_point = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
            pointExcludePoint = (TextView)findViewById(R.id.PointExcludePoint);
            pointExcludePoint.setText(Integer.toString(exclude_point));
        }
        // ポイント計算(リセット)
        if(view==button_point_reset){
            pointPrice = (EditText)findViewById(R.id.PointPrice);   // 金額
            pointPrice.setText("");
            pointRate = (EditText)findViewById(R.id.PointRate);     // ポイント率
            pointRate.setText("");
            pointGetPoint = (TextView)findViewById(R.id.PointGetPoint);
            pointGetPoint.setText("");
            pointExcludePoint = (TextView)findViewById(R.id.PointExcludePoint);
            pointExcludePoint.setText("");
        }

        // 消費税5%
        if(view==button_tax_5per_calc){
            setTax(0.05);
        }
        if(view==button_tax_8per_calc){
            setTax(0.08);
        }
        if(view==button_tax_10per_calc){
            setTax(0.1);
        }

        // 割引計算(リセット)
        if(view==button_tax_reset){
            taxPrice = (EditText)findViewById(R.id.TaxPrice);
            taxPrice.setText("");
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tax_type);
            radioGroup.check(R.id.WithoutTax);
            taxEstablishedPrice = (TextView)findViewById(R.id.EstablishedPrice);
            taxEstablishedPrice.setText("");
            taxCosumptionTax = (TextView)findViewById(R.id.CosumptionTax);
            taxCosumptionTax.setText("");
            taxTotalPrice = (TextView)findViewById(R.id.TotalPrice);
            taxTotalPrice.setText("");
        }
    }

    public void setPerPrice(int per){
        double price1 = 9999999;
        double price2 = 9999999;
        double price3 = 9999999;
        perPrice1 = (EditText)findViewById(R.id.PerPrice1);   // 金額
        perPrice2 = (EditText)findViewById(R.id.PerPrice2);
        perPrice3 = (EditText)findViewById(R.id.PerPrice3);
        perWeight1 = (EditText)findViewById(R.id.PerWeight1); // 内容量
        perWeight2 = (EditText)findViewById(R.id.PerWeight2);
        perWeight3 = (EditText)findViewById(R.id.PerWeight3);
        perWeightPrice1 = (TextView)findViewById(R.id.PerWeightPrice1);
        perWeightPrice2 = (TextView)findViewById(R.id.PerWeightPrice2);
        perWeightPrice3 = (TextView)findViewById(R.id.PerWeightPrice3);
        String per_weight_per_price = getString(R.string.per_weight_per_price);
        per_weight_per_price = per_weight_per_price.replaceAll("%per",Integer.toString(per));
        perWeightPerPrice = (TextView)findViewById(R.id.PerWeightPerPrice);
        perWeightPerPrice.setText(per_weight_per_price);
        if (perPrice1.getText().toString().equals("") == false && perWeight1.getText().toString().equals("") == false){
            double per_price1 = Double.valueOf(perPrice1.getText().toString());
            double per_weight1 = Double.valueOf(perWeight1.getText().toString());
            price1 = per_price1 * per / per_weight1;
            BigDecimal bd = new BigDecimal(price1);
            price1 = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
            perWeightPrice1.setText(Double.toString(price1));
        } else {
            perWeightPrice1.setText("0");
        }
        if (perPrice2.getText().toString().equals("") == false && perWeight2.getText().toString().equals("") == false){
            double per_price2 = Double.valueOf(perPrice2.getText().toString());
            double per_weight2 = Double.valueOf(perWeight2.getText().toString());
            price2 = per_price2 * per / per_weight2;
            BigDecimal bd = new BigDecimal(price2);
            price2 = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
            perWeightPrice2.setText(Double.toString(price2));
        } else {
            perWeightPrice2.setText("0");
        }
        if (perPrice3.getText().toString().equals("") == false && perWeight3.getText().toString().equals("") == false){
            double per_price3 = Double.valueOf(perPrice3.getText().toString());
            double per_weight3 = Double.valueOf(perWeight3.getText().toString());
            price3 = per_price3 * per / per_weight3;
            BigDecimal bd = new BigDecimal(price3);
            price3 = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
            perWeightPrice3.setText(Double.toString(price3));
        } else {
            perWeightPrice3.setText("0");
        }
        if (price1 > 0 && price1 <= price2 && price1 <= price3){
            perWeightPrice1.setTextColor(0xffff0000);
        } else {
            perWeightPrice1.setTextColor(0xffff9900);
        }
        if (price2 > 0 && price2 <= price1 && price2 <= price3){
            perWeightPrice2.setTextColor(0xffff0000);
        } else {
            perWeightPrice2.setTextColor(0xffff9900);
        }
        if (price3 > 0 && price3 <= price1 && price3 <= price2){
            perWeightPrice3.setTextColor(0xffff0000);
        } else {
            perWeightPrice3.setTextColor(0xffff9900);
        }
    }

    public void setTax(double tax_rate){
        int established_price = 0;
        int tax = 0;
        int total = 0;
        taxPrice = (EditText)findViewById(R.id.TaxPrice);   // 金額
        if (taxPrice.getText().toString().equals("")){
            showDialog(this, "", getString(R.string.tax_error));
            return;
        }
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tax_type);
        int radio_id = radioGroup.getCheckedRadioButtonId();
        if (radio_id == R.id.IncludeTax) {
            established_price = (int)Math.round(Double.valueOf(taxPrice.getText().toString()) / (1 + tax_rate));
            total = Integer.valueOf(taxPrice.getText().toString());
            tax = total - established_price;
        } else {
            established_price = Integer.valueOf(taxPrice.getText().toString());
            tax = (int)Math.floor(Double.valueOf(taxPrice.getText().toString()) * tax_rate);
            total = established_price + tax;
        }
        taxEstablishedPrice = (TextView)findViewById(R.id.EstablishedPrice);
        taxEstablishedPrice.setText(Integer.toString(established_price));
        taxCosumptionTax = (TextView)findViewById(R.id.CosumptionTax);
        taxCosumptionTax.setText(Integer.toString(tax));
        taxTotalPrice = (TextView)findViewById(R.id.TotalPrice);
        taxTotalPrice.setText(Integer.toString(total));
    }

    private static void showDialog(final Activity activity, String title, String message){
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton){
                activity.setResult(Activity.RESULT_OK);
                }
            });
        ad.create();
        ad.show();
    }
}