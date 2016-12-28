//
//  ViewController.swift
//  my2048swift
//
//  Created by Meiyi He on 12/22/16.
//  Copyright © 2016 Meiyi He. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @IBAction func startGame(_ sender:UIButton){
        
        self.present(MainTabViewController(), animated: true, completion: nil)
    }

}

