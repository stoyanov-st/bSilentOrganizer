package bg.uni_ruse.stoyanov.bsilentorganizer.note;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

import bg.uni_ruse.stoyanov.bsilentorganizer.MainActivity;
import bg.uni_ruse.stoyanov.bsilentorganizer.R;

import static bg.uni_ruse.stoyanov.bsilentorganizer.note.NoteList.getNotes;

/**
 * Created by stoyanovst on 8/29/17.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0 , notes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_list_item_layout, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.note_title);
        TextView previewTextView = convertView.findViewById(R.id.note_preview);

        if (note != null) {
            titleTextView.setText(note.getNoteTitle());
            previewTextView.setText(note.getNoteText());
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.delete_item, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        NoteDao noteDao = MainActivity.getDaoSession().getNoteDao();
                        noteDao.delete(note);
                        clear();
                        addAll(getNotes(getContext()));
                        notifyDataSetChanged();
                        return true;
                    }
                });
                return true;
            }
        });

        return convertView;
    }

}
