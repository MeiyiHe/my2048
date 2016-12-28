//: Playground - noun: a place where people can play

import UIKit


var multiArr:[[Int]] = Array(repeating: Array(repeating: 0, count: 4), count: 4)

print(multiArr)

multiArr[0][0] = 1
multiArr[0][1] = 1
multiArr[0][2] = 1
multiArr[0][3] = 1

print(multiArr)



var str = "Hello, playground"

var i:Int = 0
for index in 1...9
{
    print(index)
}



//create a button
let button = UIButton(frame:CGRect(x: 100, y: 100, width: 100, height: 50))
// set the title for the button
button.setTitle( "Hello", for:.normal)
button.titleLabel?.textColor = UIColor.white
button.backgroundColor = UIColor.green














