//
//  TileView.swift
//  my2048swift
//
//  Created by Meiyi He on 12/25/16.
//  Copyright © 2016 Meiyi He. All rights reserved.
//

import UIKit

class TileView:UIView{
    
    let colorMap = [
        2:UIColor.yellow,
        4:UIColor.orange,
        8:UIColor.red,
        16:UIColor.green,
        32:UIColor.brown,
        64:UIColor.blue,
        128:UIColor.purple,
        256:UIColor.cyan,
        512:UIColor.lightGray,
        1024:UIColor.magenta,
        2048:UIColor.black
    ]
    
    var value:Int = 0{
        
        // when value changes, the didSet will be called to change the color and label
        didSet{
            backgroundColor = colorMap[value]
            numberLabel.text = "\(value)"
        }
    }
    
    var numberLabel:UILabel
    
    init(pos:CGPoint, width:CGFloat, value:Int){
        numberLabel = UILabel(frame: CGRect( x: 0, y: 0,width: width, height: width))
        numberLabel.textColor = UIColor.white
        numberLabel.textAlignment = NSTextAlignment.center
        numberLabel.minimumScaleFactor = 0.5
        numberLabel.font = UIFont(name:"Sans Seriff", size:20)
        numberLabel.text = "\(value)"
        super.init(frame: CGRect( x: pos.x, y: pos.y  ,width: width, height: width))
        addSubview(numberLabel)
        self.value = value
        backgroundColor = colorMap[value]
        
        
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
