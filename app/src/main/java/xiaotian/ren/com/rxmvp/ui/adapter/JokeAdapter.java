package xiaotian.ren.com.rxmvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xiaotian.ren.com.rxmvp.R;
import xiaotian.ren.com.rxmvp.interfa.BaseData;
import xiaotian.ren.com.rxmvp.model.ContentlistEntity;
import xiaotian.ren.com.rxmvp.util.AnimUtil;
import xiaotian.ren.com.rxmvp.util.TimeUtil;

/**
 * Created by JDD on 2016/4/23 0023.
 */
public class JokeAdapter extends RecyclerView.Adapter {

    private List<BaseData> jokeList;

    public JokeAdapter(List<BaseData> jokeList) {
        this.jokeList = jokeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_list_item,
                parent, false);
        JokeViewHolder holder = new JokeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JokeViewHolder jokeViewHolder = (JokeViewHolder) holder;
        runEnterItemAnim(jokeViewHolder.ll_content, position);
        ContentlistEntity jokeData = (ContentlistEntity) jokeList.get(position);
        jokeViewHolder.title.setText("#" + jokeData.getTitle() + "#");
        jokeViewHolder.time.setText(TimeUtil.getDateBySplit(jokeData.getCt()));
        /*使html中<标签>得以转化*/
        jokeViewHolder.content.setText(Html.fromHtml(jokeData.getText().toString()));


    }

    private void runEnterItemAnim(View viewHolder,int position) {
        AnimUtil.listItemUpAnim(viewHolder,position,null);
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }

    static class JokeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.ll_content)
        LinearLayout ll_content;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.content)
        TextView content;

        public JokeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
