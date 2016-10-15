package com.example.septiawanajip.printnet.Tab;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.septiawanajip.printnet.Activity.MainActivity;
import com.example.septiawanajip.printnet.R;
import com.example.septiawanajip.printnet.ServerConfiguration.Template;
import com.example.septiawanajip.printnet.Utils.FileManager;
import com.example.septiawanajip.printnet.Utils.MultiPartRequest;
import com.example.septiawanajip.printnet.Utils.StringParser;
import com.example.septiawanajip.printnet.Utils.VolleySingleton;

import java.io.File;

/**
 * Created by Septiawan Aji P on 10/13/2016.
 */
public class OneFragment extends Fragment {

    MultiPartRequest mMultiPartRequest;
    RequestQueue mRequest;
    Button mAdd,mUpload;
    TextView mInfo, mResponse;
    File mFile;
    private Uri mOutputUri;

    public OneFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_one,container,false);
        mAdd = (Button)view.findViewById(R.id.add);
        mUpload = (Button)view.findViewById(R.id.upload);

        mInfo = (TextView)view.findViewById(R.id.file_info);
        mResponse = (TextView)view.findViewById(R.id.response);

        mRequest = VolleySingleton.getInstance().getRequestQueue();

//        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Gaitera_Ball-demo-FFP.ttf");
//        judul.setTypeface(type);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextView) v).getText().equals("Delete")){
                    resetView();
                }else{
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("pdf/* docs/*");
                    startActivityForResult(intent, Template.Code.FILE_MANAGER_CODE);
                }

            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                mUpload.setVisibility(Button.INVISIBLE);
            }
        });
        return view;
    }
    void uploadFile() {
        mRequest.start();
        mMultiPartRequest = new MultiPartRequest(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mUpload.setVisibility(Button.VISIBLE);
                setResponse(null, error);
            }
        }, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                mUpload.setVisibility(Button.VISIBLE);
                setResponse(response,null);
            }
        }, mFile);
        //Set tag, diperlukan ketika akan menggagalkan request/cancenl request
        mMultiPartRequest.setTag("MultiRequest");
        //Set retry policy, untuk mengatur socket time out, retries. Bisa disetting lewat template
        mMultiPartRequest.setRetryPolicy(new DefaultRetryPolicy(Template.VolleyRetryPolicy.SOCKET_TIMEOUT,
                Template.VolleyRetryPolicy.RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Menambahkan ke request queue untuk diproses
        mRequest.add(mMultiPartRequest);
    }

    void setFile(int type, Uri uri) {
        mFile = new File(FileManager.getPath(getContext(), type, uri));
    }

    void setView(int type, Uri uri) {
        mUpload.setVisibility(Button.VISIBLE);
        mAdd.setText("Delete");
        mInfo.setVisibility(TextView.VISIBLE);
        mInfo.setText("File info\n" + "Name : " + mFile.getName() + "\nSize : " +
                FileManager.getSize(mFile.length(), true));

    }

    void resetView() {
        mUpload.setVisibility(Button.GONE);
        mInfo.setVisibility(TextView.GONE);
        mInfo.setText("");
        mResponse.setText("");
        mAdd.setText("Add");
    }

    //Respon dari volley, untuk menampilkan keterengan upload, seperti error, message dari server
    void setResponse(Object response, VolleyError error) {
        if (response == null) {
            mResponse.setText("Error\n" + error);
        } else {
            if (StringParser.getCode(response.toString()).equals(Template.Query.VALUE_CODE_SUCCESS))
                mResponse.setText("Success\n" + StringParser.getMessage(response.toString()));
            else
                mResponse.setText("Error\n" + StringParser.getMessage(response.toString()));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == Template.Code.FILE_MANAGER_CODE) {
                setFile(requestCode, data.getData());
                setView(requestCode, data.getData());
            } else {
                setFile(requestCode, mOutputUri);
                setView(requestCode, mOutputUri);
            }

        } else {
            resetView();
        }
    }
}
