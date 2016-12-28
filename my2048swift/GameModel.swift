//
//  GameModel.swift
//  my2048swift
//
//  Created by Meiyi He on 12/28/16.
//  Copyright © 2016 Meiyi He. All rights reserved.
//

import Foundation

class GameModel{
    
    var dimension:Int = 0
    
    var tiles:Array<Int>
    
    init(dimension:Int){
        self.dimension = dimension
        self.tiles = Array<Int>( repeating: 0, count: self.dimension*self.dimension)
    }
    
    func setPosition(row:Int, col:Int, value:Int) -> Bool{
        assert(row >= 0 && row < dimension);
        assert(col >= 0 && col < dimension);
        let index = self.dimension * row + col
        let value = tiles[index]
        if(value > 0){
            print("this position contains number already")
            return false
        }
        tiles[index] = value
        return true
    }
}
