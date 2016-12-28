//
//  MainViewController.swift
//  my2048swift
//
//  Created by Meiyi He on 12/22/16.
//  Copyright © 2016 Meiyi He. All rights reserved.
//

import UIKit

// main view page of game

class MainViewController: UIViewController{

    var dimension:Int = 3
    var maxNumber:Int = 2048
    
    // width per grid
    var width:CGFloat = 50
    // padding between grids --> use CGFloat since CGMake requires as parameter
    var padding:CGFloat = 6
    
    // declare a background array
    var backgrounds: Array<UIView>
    
    /*
     * Game initialization
     */
    init(){
        // background array initialization
        self.backgrounds = Array<UIView>()

        super.init( nibName:nil, bundle:nil )
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // show background grid
        setupBackground()
        randomNumber()
    }

    func setupBackground(){
        
        var x:CGFloat = 30
        var y:CGFloat = 150
        
        for _ in 0 ... dimension{
            y = 150
            
            for _ in 0 ... dimension{
                
                let  background = UIView(frame: CGRect( x: x, y: y,width: width, height: width))
                background.backgroundColor = UIColor.darkGray
                self.view.addSubview(background)
                
                // add current background into background array
                backgrounds.append(background)
                
                // change to the next col
                y += padding + width
            }
            //change to the next row
            x += padding + width
        }
    }
    
    func randomNumber(){
        
        let random = Int(arc4random_uniform(10))
          print(random)
        
        
        var seed:Int = 2
        if(random == 1){
            seed = 4
        }
        
        let col = Int(arc4random_uniform(UInt32(dimension)))
        let row = Int(arc4random_uniform(UInt32(dimension)))
        
        insertTile(pos: (row,col), value: seed)
    }
    
    
    func insertTile( pos:(Int, Int), value:Int){
        let (row, col) = pos;
        
        let x = 30 + CGFloat(col) * (width + padding)
        let y = 150 + CGFloat(row) * (width + padding)
        
        let tile = TileView( pos: CGPoint( x: x, y: y), width: width, value: value)
        self.view.addSubview(tile)
        self.view.bringSubview(toFront: tile)
        
        // TODO --> need review and understand
        tile.layer.setAffineTransform(CGAffineTransform(scaleX: 0.1,y: 0.1))
        UIView.animate(withDuration: 0.3, delay: 0.1, options:[], animations:{
                ()-> Void in
                tile.layer.setAffineTransform(CGAffineTransform(scaleX: 1,y: 1))
        },completion: {
            
            (finished:Bool) -> Void in
            
            UIView.animate(withDuration: 0.08, animations:{
                ()-> Void in
                //完成动画时，数字块复原
                tile.layer.setAffineTransform(CGAffineTransform.identity)
            })
        })
    }
    
 

}
