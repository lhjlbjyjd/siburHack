package ua.com.lhjlbjyjd.sibur;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lhjlbjyjd on 25.11.2017.
 */

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.ViewHolder> {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Goal[] mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        RelativeLayout layout;
        public ViewHolder(RelativeLayout v) {
            super(v);
            layout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GoalListAdapter(Goal[] myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GoalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        RelativeLayout v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_item, parent, false).findViewById(R.id.goal_layout);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView)holder.layout.findViewById(R.id.goal_description_text)).setText(mDataset[position].getDescription());
        if(!mDataset[position].getState()) {
            holder.layout.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
                    builder.setTitle("Подтверждение");
                    builder.setMessage("Вы подтверждаете выполнение задачи?");
                    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            view.setEnabled(false);
                            mDataset[position].setState(true);
                        }
                    });
                    builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((CheckBox) view).setChecked(false);
                        }
                    });
                    builder.show();
                }
            });
        }else{
            CheckBox checkBox = holder.layout.findViewById(R.id.checkBox);
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
        }

        ImageView additionalTaskButton = holder.layout.findViewById(R.id.additional_task);
        additionalTaskButton.setVisibility(View.GONE);

        if(mDataset[position].getEmailRequired()){
            additionalTaskButton.setVisibility(View.VISIBLE);
            additionalTaskButton.setBackgroundColor(Color.parseColor("#CB213D"));
        }
        if(mDataset[position].getPhotoRequired()){
            additionalTaskButton.setVisibility(View.VISIBLE);
            additionalTaskButton.setBackgroundColor(Color.parseColor("#43ff98"));
            additionalTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        ((GoalsListActivity)context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
