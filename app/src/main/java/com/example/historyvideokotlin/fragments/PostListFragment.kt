package com.example.historyvideokotlin.fragments

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.DetailActivity
import com.example.historyvideokotlin.adapters.PostListAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostListBinding
import com.example.historyvideokotlin.model.PostListData
import com.example.historyvideokotlin.utils.Constants.DETAIL_KEY
import com.example.historyvideokotlin.utils.Constants.POST_DATA_KEY
import com.example.historyvideokotlin.utils.Constants.POST_DETAIL_KEY
import com.example.historyvideokotlin.viewmodels.PostViewModel
import java.util.*

class PostListFragment : BaseFragment<PostViewModel, FragmentPostListBinding>(),
    PostListAdapter.OnItemClickListener {
    private var postType: Int = 0
    private var adapter: PostListAdapter? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_post_list
    }

    override fun getViewModel(): PostViewModel =
        ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        postType = arguments?.getInt(KEY_POST_LIST_TYPE)!!

        var postList = mutableListOf<PostListData>()
        var title = ""
        var year = ""
        var description = "mota"
        var image = ""
        var content = ""
        var postListData: PostListData

        when (postType) {
            0 -> {
                viewModel.postPerson.observe(this, { data ->
                    data.let {
                        postList.clear()
                        for (i in 0 until data.size) {
                            title = data.get(i).post_name
                            year = data.get(i).year
                            if (data.get(i).image != null) {
                                image = data.get(i).image
                            } else {
                                image = ""
                            }
                            content = data.get(i).content

                            postListData = PostListData(title, year, description, image, content)
                            postList.add(postListData)
                        }
                        setRecyclerView(postList)
                    }
                }
                )

            }
            1 -> {
                viewModel.postEvent.observe(this, { data ->
                    data.let {
                        postList.clear()
                        for (i in 0 until data.size) {
                            title = data.get(i).title
                            year = data.get(i).year
                            if (data.get(i).image != null) {
                                image = data.get(i).image
                            } else {
                                image = ""
                            }
                            content = data.get(i).content
                            postListData = PostListData(title, year, description, image, content)
                            postList.add(postListData)
                        }
                        setRecyclerView(postList)
                    }
                }
                )
            }
            2 -> {
                viewModel.postPlace.observe(this, { data ->
                    data.let {
                        postList.clear()
                        for (i in 0 until data.size) {

                            title = data.get(i).post_name
                            year = data.get(i).place
                            if (data.get(i).image != null) {
                                image = data.get(i).image
                            } else {
                                image = ""
                            }
                            content = data.get(i).content
                            postListData = PostListData(title, year, description, image, content)
                            postList.add(postListData)
                        }
                        setRecyclerView(postList)
                    }
                }
                )
            }
            else -> {
                viewModel.postTimeline.observe(this, { data ->
                    data.let {
                        postList.clear()
                        for (i in 0 until data.size) {

                            title = data.get(i).title
                            year = data.get(i).year
                            if (data.get(i).image != null) {
                                image = data.get(i).image
                            } else {
                                image = ""
                            }
                            content = data.get(i).content
                            postListData = PostListData(title, year, description, image, content)
                            postList.add(postListData)
                        }
                        setRecyclerView(postList)
                    }
                }
                )
            }
        }
    }

    private fun setRecyclerView(postList: List<PostListData>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = PostListAdapter(postList, requireContext(), this)
        binding.recyclerViewPost.setHasFixedSize(true)
        binding.recyclerViewPost.layoutManager = linearLayoutManager
        binding.recyclerViewPost.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        const val KEY_POST_LIST_TYPE = "KEY_POST_LIST_TYPE"

        @JvmStatic
        fun newInstance(postType: Int) =
            PostListFragment().apply {
                arguments = bundleOf(
                    KEY_POST_LIST_TYPE to postType
                )
            }
    }

    override fun onItemClick(postListData: PostListData) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, POST_DETAIL_KEY)
        intent.putExtra(POST_DATA_KEY, postListData)
        requireActivity().startActivity(intent)
//        pushFragment(
//            PostDetailFragment.newInstance(postListData),
//            Utils.getSlideTransitionAnimationOptions()
//        )
    }
}