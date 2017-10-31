package com.harshadachavan.asynctaskdemo;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    //Creating references of the classes, elements of which are used in the layout.
    Button saveBtn,deleteBtn;
    EditText enteredData;
    TextView showData;

    @Override
    //onCreate method.
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   //Setting content view.

        //Setting references with their Ids.
        saveBtn=(Button)findViewById(R.id.add_data_btn);
        deleteBtn=(Button)findViewById(R.id.delete_data_btn);
        enteredData=(EditText)findViewById(R.id.file_data_et);
        showData=(TextView)findViewById(R.id.file_data_tv);

        //Setting onClick event for Save Button.
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Creating object of LoadFileInBack class.
                LoadFileInBack myObject = new LoadFileInBack();

                //Executing object of class and passed parameter as EditText value.
                myObject.execute(enteredData.getText().toString());
            }
        });

        //Setting onClick event for delete Button.
        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Detecting File with full path as shown
                File deletingFile = new File(getExternalFilesDir("myDir").getAbsolutePath()+"/"+"Example.txt");
                boolean result = deletingFile.delete();    //Deleting file and fetching boolean result.

                //Checking if file is deleted or not.
                if(result) {
                    Toast.makeText(getApplicationContext(), "File Deleted", Toast.LENGTH_LONG).show();

                    //if file is deleted, then set values of TextView and EditText as by default.
                    enteredData.setText("");
                    showData.setText("No data Inseted in File");
                }
                else
                {
                    //Displaying Toast.
                    Toast.makeText(getApplicationContext(), "File not Deleted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //Creating nested class by extending AsyncTask class
    class LoadFileInBack extends AsyncTask<String,Void,String>
    {

        @Override
        //Method to run seperate thread in background.
        protected String doInBackground(String... params)
        {
            //String passed while calling...
            String dataToInsert = params[0];
            try
            {
                //creating OutputStream in the file as shown as below path.
                FileOutputStream fos = new FileOutputStream(new File(getExternalFilesDir("myDir"),"Example.txt"));

                //Writing the file with the value inserted in EditText.
                fos.write(dataToInsert.getBytes());

                //Displaying Toast.
                Toast.makeText(getApplicationContext(),"File :"+"Example"+".txt Saved !",Toast.LENGTH_LONG).show();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        //Method runs on the UI thread.
        protected void onPostExecute(String s)
        {
            String decodedData = "";

            try
            {
                //FileInputStream to open the existing file to read.
                FileInputStream fin = new FileInputStream(new File(getExternalFilesDir("myDir"),"Example.txt"));

                int c;
                //while loop to read file character by character.
                while((c=fin.read())!=-1)
                {
                    decodedData += String.valueOf((char)c);
                }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            //Setting text to TextView.
            showData.setText(decodedData);
            super.onPostExecute(s);
        }
    }
}
