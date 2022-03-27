package com.example.barbon;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText prodID, prodName,prodDesc, pPrice, pQty;
    Button createBtn, updateBtn,retrieveIDBtn,reviewBtn,deleteBtn;
    ProductDB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prodID = (EditText) findViewById(R.id.product_id);
        prodName = findViewById(R.id.product_name);
        prodDesc = findViewById(R.id.product_desc);
        pPrice = findViewById(R.id.prod_price);
        pQty = findViewById(R.id.qty);

        createBtn = findViewById(R.id.createbtn);
        updateBtn = findViewById(R.id.updatebtn);
        retrieveIDBtn = findViewById(R.id.retrieveIDbtn);
        reviewBtn = findViewById(R.id.retviewbtn);
        deleteBtn = findViewById(R.id.deletebtn);

        DB = new ProductDB(this);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prodID1 = prodID.getText().toString();
                String name = prodName.getText().toString();
                String desc = prodDesc.getText().toString();
                String price = pPrice.getText().toString();
                String qty = pQty.getText().toString();

                if (prodID1.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !price.isEmpty() && !qty.isEmpty()){
                    prodID.setError("Product ID is REQUIRED");
                    prodID.requestFocus();
                    return;
                }
                if (name.isEmpty() && !prodID1.isEmpty() && !desc.isEmpty() && !price.isEmpty() && !qty.isEmpty()){
                    prodName.setError("Product Name is REQUIRED");
                    prodName.requestFocus();
                    return;
                }
                if (desc.isEmpty() && !prodID1.isEmpty() && !name.isEmpty() && !price.isEmpty() && !qty.isEmpty()){
                    prodDesc.setError("Product Description is REQUIRED");
                    prodDesc.requestFocus();
                    return;
                }
                if (price.isEmpty() && !prodID1.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !qty.isEmpty()){
                    pPrice.setError("Product Price is REQUIRED");
                    pPrice.requestFocus();
                    return;
                }
                if (qty.isEmpty() && !prodID1.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !price.isEmpty()){
                    pQty.setError("Product Quantity is  REQUIRED");
                    pQty.requestFocus();
                    return;
                }

                if( prodID1.isEmpty() || name.isEmpty() || desc.isEmpty() || price.isEmpty() || qty.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkInsertData = DB.insertuserdata(prodID1, name, desc, price, qty);
                    if (checkInsertData == true){
                        Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        prodID.getText().clear();
                        prodName.getText().clear();
                        prodDesc.getText().clear();
                        pPrice.getText().clear();
                        pQty.getText().clear();
                    }
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prodID1 = prodID.getText().toString();
                String name = prodName.getText().toString();
                String desc = prodDesc.getText().toString();
                String price = pPrice.getText().toString();
                String qty = pQty.getText().toString();

                if (prodID1.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !price.isEmpty() && !qty.isEmpty()){
                    prodID.setError("Product ID is REQUIRED");
                    prodID.requestFocus();
                    return;
                }
                if (name.isEmpty() && !prodID1.isEmpty() && !desc.isEmpty() && !price.isEmpty() && !qty.isEmpty()){
                    prodName.setError("Product Name is REQUIRED");
                    prodName.requestFocus();
                    return;
                }
                if (desc.isEmpty() && !prodID1.isEmpty() && !name.isEmpty() && !price.isEmpty() && !qty.isEmpty()){
                    prodDesc.setError("Product Description is REQUIRED");
                    prodDesc.requestFocus();
                    return;
                }
                if (price.isEmpty() && !prodID1.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !qty.isEmpty()){
                    pPrice.setError("Product Price is REQUIRED");
                    pPrice.requestFocus();
                    return;
                }
                if (qty.isEmpty() && !prodID1.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !price.isEmpty()){
                    pQty.setError("Product Quantity is  REQUIRED");
                    pQty.requestFocus();
                    return;
                }
                if( prodID1.isEmpty() || name.isEmpty() || desc.isEmpty() || price.isEmpty() || qty.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkUpdateData = DB.updateuserdata(prodID1, name, desc, price, qty);
                    if (checkUpdateData == true) {
                        Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                        prodID.getText().clear();
                        prodName.getText().clear();
                        prodDesc.getText().clear();
                        pPrice.getText().clear();
                        pQty.getText().clear();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Invalid product ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = DB.getdata();
                if (cursor.getCount() == 0){
                    Toast.makeText(MainActivity.this, "No Product/s Exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buff = new StringBuffer();
                while (cursor.moveToNext()){
                    buff.append("ID:" + cursor.getString(0) + "\n");
                    buff.append("Name:"  + cursor.getString(1) + "\n");
                    buff.append("Description: " + cursor.getString(2) + "\n");
                    buff.append("Price: " + cursor.getString(3) + "\n");
                    buff.append("Quantity: " + cursor.getString(4) + "\n\n");
                }
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Products");
                builder.setMessage(buff.toString());
                builder.show();
            }
        });

        retrieveIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = prodID.getText().toString();
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("Product.db",Context.MODE_PRIVATE,null);
                Cursor b = simpledb.rawQuery("Select * from ProInventory where id ='"+a+"'",null);
                if (b.getCount() == 0)
                {
                    Toast.makeText(MainActivity.this, "No Product/s Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buff = new StringBuffer();
                while (b.moveToNext()) {
                    buff.append("ID: " + b.getString(0) + "\n");
                    buff.append("Name: " + b.getString(1) + "\n");
                    buff.append("Description: " + b.getString(2) + "\n");
                    buff.append("Price: " + b.getString(3) + "\n");
                    buff.append("Quantity: " + b.getString(4) + "\n\n");
                }
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Products");
                builder.setMessage(buff.toString());
                builder.show();
                }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prodID1 = prodID.getText().toString();
                Boolean checkdeletedata = DB.deletedata(prodID1);

                if(prodID1.isEmpty()){
                    prodID.setError("Product ID is REQUIRED");
                    prodID.requestFocus();
                    return;
                }
                if (checkdeletedata == true) {
                    Toast.makeText(MainActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                    prodID.getText().clear();
                } else
                    Toast.makeText(MainActivity.this, "Invalid product ID", Toast.LENGTH_SHORT).show();
            }
        });



    }
}