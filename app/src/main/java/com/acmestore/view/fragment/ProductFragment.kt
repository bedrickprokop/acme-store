package com.acmestore.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.acmestore.R
import com.acmestore.databinding.FragProductBinding

class ProductFragment : Fragment() {

    //private val args: ProductFragmentArgs by navArgs()
    lateinit var bind: FragProductBinding

    // setup data binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // we're using data binding in this example
        bind = DataBindingUtil.inflate(inflater, R.layout.frag_product, container, false)
        return bind.root
    }

    // enable the options menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return false
    }

    // setup the toolbar and viewmodel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity: AppCompatActivity = activity as AppCompatActivity

        // toolbar setup
        activity.setSupportActionBar(bind.toolbar)
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        //val client = StreamChat.getInstance(activity.application)
        bind.lifecycleOwner = this
        //val channel = client.channel(args.channelType, args.channelId)
        //val factory = ProductViewModelFactory(activity.application, channel)
        //val viewModel: ProductViewModel by viewModels { factory }

        // connect the view model
        // binding.viewModel = viewModel
        //binding.messageList.setViewModel(viewModel, this)
        //binding.messageInputView.setViewModel(viewModel, this)

        //view?.findViewById<MessageListView>(R.id.messageList)?.let {
        //    val otherUsers: List<User> = channel.channelState.otherUsers
        //    binding.avatarGroup.setChannelAndLastActiveUsers(channel, otherUsers, it.style)
        //}
        // binding.channelName.text = channel.name
    }
}