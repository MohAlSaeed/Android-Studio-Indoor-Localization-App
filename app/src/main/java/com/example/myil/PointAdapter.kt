package com.example.myil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myil.databinding.ListElementBinding

class PointAdapter(val context: Context, private val pointViewModel: PointViewModel) : RecyclerView.Adapter<PointAdapter.PointViewHolder>() {

    private var listOfPoints = emptyList<Point>()
//      class PointViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        class PointViewHolder (val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root){    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ListElementBinding.inflate(inflater)
        return PointViewHolder(binding)
    }

    override fun onBindViewHolder(holderPoint: PointViewHolder, position: Int) {
        val currentPoint = listOfPoints[position]
        holderPoint.binding.rvap1.text = currentPoint.ap1
        holderPoint.binding.rvr1.text = currentPoint.r1.toString()
        holderPoint.binding.rvx1.text = currentPoint.x1.toString()
        holderPoint.binding.rvy1.text = currentPoint.y1.toString()
        holderPoint.binding.rvap2.text = currentPoint.ap2
        holderPoint.binding.rvr2.text = currentPoint.r2.toString()
        holderPoint.binding.rvx2.text = currentPoint.x2.toString()
        holderPoint.binding.rvy2.text = currentPoint.y2.toString()
        holderPoint.binding.rvap3.text = currentPoint.ap3
        holderPoint.binding.rvr3.text = currentPoint.r3.toString()
        holderPoint.binding.rvx3.text = currentPoint.x3.toString()
        holderPoint.binding.rvy3.text = currentPoint.y3.toString()
        holderPoint.binding.rvx.text = currentPoint.x.toString()
        holderPoint.binding.rvy.text = currentPoint.y.toString()
        holderPoint.binding.rvid.text = currentPoint.id.toString()
        holderPoint.itemView.setOnLongClickListener {
            pointViewModel.delete(currentPoint)
            true
        }
    }

    override fun getItemCount(): Int = listOfPoints.size

    fun setPoints(points: List<Point>){
        listOfPoints = points
        notifyDataSetChanged()
    }
}