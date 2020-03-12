package com.TeamT3N.fukiosk.ui.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.TeamT3N.fukiosk.Base64;
import com.TeamT3N.fukiosk.ImageGallery;
import com.TeamT3N.fukiosk.SessionHandler;
import com.TeamT3N.fukiosk.LoginActivity;
import com.TeamT3N.fukiosk.R;
import com.TeamT3N.fukiosk.SplashScreen;
import com.TeamT3N.fukiosk.Student;
import com.TeamT3N.fukiosk.globalconfig;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Option_classes;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Option_clearance;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Option_evaluation;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Option_grades;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Option_payables;
import com.TeamT3N.fukiosk.ui.Profile.ProfileOptions.Option_printouts;
import com.squareup.picasso.Picasso;

import org.apache.http.auth.MalformedChallengeException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;


public class ProfileFragment extends Fragment {
    private ProfileViewModel profileFragmentViewModel;
    //for session to be imported
    private SessionHandler session;
    private String ip = globalconfig.ipaddress;


    //End imagepicker

    //Profile refresh
    @Override
    public void onResume()
    {
        super.onResume();
        showimage();
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        //detect if the user is logged in
        super.onCreate(savedInstanceState);
session = new SessionHandler(getContext().getApplicationContext());



        profileFragmentViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        ProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            //Locked





             @Override
            public void onChanged(@Nullable String s) {


                Student student = session.getStudentDetails();

                getView().findViewById(R.id.profile);

                //get info
                TextView name = getView().findViewById(R.id.name);
                //details display
                TextView detail_idnumber = getView().findViewById(R.id.idnumber1);
                TextView detail_Program = getView().findViewById(R.id.coursep);
                TextView detail_colleges = getView().findViewById(R.id.collegep);
                TextView detail_Yearlevel = getView().findViewById(R.id.collegesp);

                detail_idnumber.setText("ID number: ");
                detail_Program.setText("Program: ");
                detail_colleges.setText("Colleges: ");
                detail_Yearlevel.setText("Year level: ");




                TextView idnumber = getView().findViewById(R.id.idnumber);
                TextView course = getView().findViewById(R.id.course);
                TextView colleges = getView().findViewById(R.id.colleges);
                TextView yearlevel = getView().findViewById(R.id.yearlevel);
/*
                try {


                ImageView i = getView().findViewById(R.id.profile);
                String imagename=student.getImage();
                String imageurl=ip+"student/img/"+imagename;

                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URI(imageurl).getContent());
                    i.setImageBitmap(bitmap);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();

                }

 */



                name.setText(student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName());

                idnumber.setText(student.getStudentID());
                course.setText(student.getProgram());
                colleges.setText(student.getCollege());
                yearlevel.setText(student.getYear());

                //Option
                 Button option_classes = getView().findViewById(R.id.classes);
                 Button option_grades = getView().findViewById(R.id.grades);
                 Button option_payables = getView().findViewById(R.id.payables);
                 Button option_printouts = getView().findViewById(R.id.printouts);
                 Button option_evaluation = getView().findViewById(R.id.evaluation);
                 Button option_clearance = getView().findViewById(R.id.clearance);

                 option_classes.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i = new Intent(getActivity(), Option_classes.class);
                         startActivity(i);
                     }
                 });
                 option_grades.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i = new Intent(getActivity(), Option_grades.class);
                         startActivity(i);
                     }
                 });
                 option_payables.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i = new Intent(getActivity(), Option_payables.class);
                         startActivity(i);
                     }
                 });
                 option_printouts.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i = new Intent(getActivity(), Option_printouts.class);
                         startActivity(i);
                     }
                 });
                 option_evaluation.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i = new Intent(getActivity(), Option_evaluation.class);
                         startActivity(i);
                     }
                 });
                 option_clearance.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent i = new Intent(getActivity(), Option_clearance.class);
                         startActivity(i);
                     }
                 });

                 //End option

                //end info

                Button logout = getView().findViewById(R.id.btnLogout);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session.logoutStudent();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }


                });
                Button changeimg = getView().findViewById(R.id.ImageButton);
                changeimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getActivity(), ImageGallery.class);
                        startActivity(i);
                    }


                });




                //end
                textView.setText(s);

            }

        });

        return root;
    }
    public void showimage(){
        Student student = session.getStudentDetails();

        String imagename=student.getImage();
        String imageurl=ip+"student/img/"+imagename;



        ImageView profileimage= getView().findViewById(R.id.profile);
        Picasso.get().load(imageurl).into(profileimage);
    }

    //clean all elements of the recycler





    /*



            Student student = session.getStudentDetails();

            String imagename=student.getImage();
            String imageurl=ip+"student/img/"+imagename;



            ImageView profileimage= getView().findViewById(R.id.profile);
Picasso.with(context).load(imageurl).into(profileimage);
        }
    }
*/

}
