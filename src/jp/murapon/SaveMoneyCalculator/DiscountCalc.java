package jp.murapon.SaveMoneyCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DiscountCalc{

    // 割引
    EditText discountPrePrice;
    EditText discountNum;
    Spinner  discountSpinner;
    EditText discountWeight;
    TextView discountPrice;
    TextView discountDifferencePrice;
    TextView discountWeightPrice;
    Spinner  standardSpinner;
    private Button button_discount_calc;
    private Button button_discount_reset;

    public DiscountCalc(SaveMoneyCalculator main){
        // 元の金額
        discountPrePrice = (EditText)main.findViewById(R.id.DiscountPrePrice);
        discountPrePrice.setText("");
        // 割引
        discountNum = (EditText)main.findViewById(R.id.DiscountNum);
        discountNum.setText("");
        // 重さ
        discountWeight = (EditText)main.findViewById(R.id.DiscountWeight);
        discountWeight.setText("");
        // 計算するボタン
        button_discount_calc = (Button)main.findViewById(R.id.ButtonDiscountCalc);
        button_discount_calc.setOnClickListener(main);
        // リセットボタン
        button_discount_reset = (Button)main.findViewById(R.id.ButtonDiscountReset);
        button_discount_reset.setOnClickListener(main);
        // 割引種別
        discountSpinner = (Spinner)main.findViewById(R.id.DiscountKind);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(main,
                R.array.discount_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        discountSpinner.setAdapter(adapter);
    }

    public Button getButtonDiscountCalc(){
        return button_discount_calc;
    }

    public Button getButtonDiscountReset(){
        return button_discount_reset;
    }

    public void calc(SaveMoneyCalculator main, String locale){
        Double pre_price = 0.0;    // 元の金額
        int discount_num = 0;      // 割引
        int discount_id = 0;       // 割引種別
        int weight_num = 0;        // 重さ
        Double price = 0.0;        // 今の金額
        // 初期化
        discountPrice = (TextView)main.findViewById(R.id.DiscountPrice);
        discountPrice.setText("");
        discountDifferencePrice = (TextView)main.findViewById(R.id.DiscountDifferencePrice);
        discountDifferencePrice.setText("");
        discountWeightPrice = (TextView)main.findViewById(R.id.DiscountWeightPrice);
        discountWeightPrice.setText("");

        discountPrePrice = (EditText)main.findViewById(R.id.DiscountPrePrice);
        if (discountPrePrice.getText().toString().equals("")){
            showDialog(main, "", main.getString(R.string.discount_error));
            return;
        }
        pre_price = Double.valueOf(discountPrePrice.getText().toString());
        discountNum = (EditText)main.findViewById(R.id.DiscountNum);
        if (discountNum.getText().toString().equals("") == false){
            discount_num = Integer.valueOf(discountNum.getText().toString());
        }
        discount_id = discountSpinner.getSelectedItemPosition();
        discountWeight = (EditText)main.findViewById(R.id.DiscountWeight);
        if (discountWeight.getText().toString().equals("") == false){
            weight_num = Integer.valueOf(discountWeight.getText().toString());
        }

        // 元の金額、割引、重さを見て、計算する
        if (discount_num > 0){
            switch (discount_id) {
            case 0:
                // %割引の時
                if (locale.equals("ja")){
                    price = (double)Math.round(pre_price * (100 - discount_num) * 0.01);
                } else{
                    price = pre_price * (100 - discount_num) * 0.01;
                    BigDecimal bd = new BigDecimal(price);
                    price = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
                break;
            case 1:
                // 円引の時
                price = pre_price - discount_num;
                break;
            case 2:
                // 割増の時
                if (locale.equals("ja")){
                    price = (double)Math.round(pre_price * (discount_num * 0.01 + 1));
                } else{
                    price = pre_price * (discount_num * 0.01 + 1);
                    BigDecimal bd = new BigDecimal(price);
                    price = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
                break;
            default:
                break;
            }
        }

        // 割引後金額表示
        if (price > 0){
            discountPrice.setText(Double.toString(price));
            // お得金額表示
            Double difference_price = pre_price - price;
            BigDecimal bd2 = new BigDecimal(difference_price);
            difference_price = bd2.setScale(2, RoundingMode.HALF_UP).doubleValue();
            discountDifferencePrice.setText(Double.toString(difference_price));
        } else {
            price = pre_price;
        }
        // 100単位の金額表示
        if (weight_num > 0){
            int weight_price = (int)Math.floor(price * 100 / weight_num);
            discountWeightPrice.setText(Integer.toString(weight_price));
        }
    }

    public void reset(SaveMoneyCalculator main){
        // 元の金額
        discountPrePrice = (EditText)main.findViewById(R.id.DiscountPrePrice);
        discountPrePrice.setText("");
        // 割引
        discountNum = (EditText)main.findViewById(R.id.DiscountNum);
        discountNum.setText("");
        // 重さ
        discountWeight = (EditText)main.findViewById(R.id.DiscountWeight);
        discountWeight.setText("");
        // 割引後金額表示
        discountPrice = (TextView)main.findViewById(R.id.DiscountPrice);
        discountPrice.setText("");
        // お得金額表示
        discountDifferencePrice = (TextView)main.findViewById(R.id.DiscountDifferencePrice);
        discountDifferencePrice.setText("");
        // 100単位の金額表示
        discountWeightPrice = (TextView)main.findViewById(R.id.DiscountWeightPrice);
        discountWeightPrice.setText("");
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