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

    // ����
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
        // ���̋��z
        discountPrePrice = (EditText)main.findViewById(R.id.DiscountPrePrice);
        discountPrePrice.setText("");
        // ����
        discountNum = (EditText)main.findViewById(R.id.DiscountNum);
        discountNum.setText("");
        // �d��
        discountWeight = (EditText)main.findViewById(R.id.DiscountWeight);
        discountWeight.setText("");
        // �v�Z����{�^��
        button_discount_calc = (Button)main.findViewById(R.id.ButtonDiscountCalc);
        button_discount_calc.setOnClickListener(main);
        // ���Z�b�g�{�^��
        button_discount_reset = (Button)main.findViewById(R.id.ButtonDiscountReset);
        button_discount_reset.setOnClickListener(main);
        // �������
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
        Double pre_price = 0.0;    // ���̋��z
        int discount_num = 0;      // ����
        int discount_id = 0;       // �������
        int weight_num = 0;        // �d��
        Double price = 0.0;        // ���̋��z
        // ������
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

        // ���̋��z�A�����A�d�������āA�v�Z����
        if (discount_num > 0){
            switch (discount_id) {
            case 0:
                // %�����̎�
                if (locale.equals("ja")){
                    price = (double)Math.round(pre_price * (100 - discount_num) * 0.01);
                } else{
                    price = pre_price * (100 - discount_num) * 0.01;
                    BigDecimal bd = new BigDecimal(price);
                    price = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
                }
                break;
            case 1:
                // �~���̎�
                price = pre_price - discount_num;
                break;
            case 2:
                // �����̎�
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

        // ��������z�\��
        if (price > 0){
            discountPrice.setText(Double.toString(price));
            // �������z�\��
            Double difference_price = pre_price - price;
            BigDecimal bd2 = new BigDecimal(difference_price);
            difference_price = bd2.setScale(2, RoundingMode.HALF_UP).doubleValue();
            discountDifferencePrice.setText(Double.toString(difference_price));
        } else {
            price = pre_price;
        }
        // 100�P�ʂ̋��z�\��
        if (weight_num > 0){
            int weight_price = (int)Math.floor(price * 100 / weight_num);
            discountWeightPrice.setText(Integer.toString(weight_price));
        }
    }

    public void reset(SaveMoneyCalculator main){
        // ���̋��z
        discountPrePrice = (EditText)main.findViewById(R.id.DiscountPrePrice);
        discountPrePrice.setText("");
        // ����
        discountNum = (EditText)main.findViewById(R.id.DiscountNum);
        discountNum.setText("");
        // �d��
        discountWeight = (EditText)main.findViewById(R.id.DiscountWeight);
        discountWeight.setText("");
        // ��������z�\��
        discountPrice = (TextView)main.findViewById(R.id.DiscountPrice);
        discountPrice.setText("");
        // �������z�\��
        discountDifferencePrice = (TextView)main.findViewById(R.id.DiscountDifferencePrice);
        discountDifferencePrice.setText("");
        // 100�P�ʂ̋��z�\��
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