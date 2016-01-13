package cn.go.lyqdh.carmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyqdh on 2016/1/11.
 */
public class Page2Fragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private List<String> list;
    private EditText edComment;
    private ImageView ivSend;
    private ListView commentListView;

    public static Page2Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Page2Fragment pageFragment = new Page2Fragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void iniitData() {
        list = new ArrayList<String>();
        list.add("太好啦");
        list.add("真棒！");
        list.add("超赞");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        commentListView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_diver_tab2, container, false);
        edComment = (EditText) view.findViewById(R.id.et_edit_content);
        ivSend = (ImageView) view.findViewById(R.id.iv_send);
        commentListView = (ListView) view.findViewById(R.id.comment_list);
        iniitData();
        iniListener();
        return view;
    }

    private void iniListener() {
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edComment.getText().toString().trim();
                if (comment != null) {
                    list.add(comment);
                    edComment.setHint("请输入评论");
                }
            }
        });
    }
}
