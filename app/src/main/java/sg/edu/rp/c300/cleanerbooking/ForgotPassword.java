package sg.edu.rp.c300.cleanerbooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    Button forgotPass;
    EditText Name,Mobile;
    String mobile,name;
    StringRequest stringRequest;
    String URL ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPass = findViewById(R.id.btnForgotPass);
        Name = findViewById(R.id.editTextforgotName);
        Mobile = findViewById(R.id.editTextforgotMobile);

        submit();

    }

    private void submit(){

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = Mobile.getText().toString();
                name = Name.getText().toString();

                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(" Success")){
                            Toast.makeText(getApplicationContext(),"Email successfully sent; Please check your inbox.",Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(getApplicationContext(),"Failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Please Check Connection.",Toast.LENGTH_LONG).show();
                    }

            }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError{
                        Map<String, String> params = new HashMap<>();
                        params.put("Mobile",mobile);
                        params.put("Name",name);

                        return super.getParams();

                    }
                };
            }
        });
    }
}
