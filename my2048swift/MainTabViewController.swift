//
//  MainTabViewController.swift
//  my2048swift
//
//  Created by Meiyi He on 12/22/16.
//  Copyright © 2016 Meiyi He. All rights reserved.
//

import UIKit

class MainTabViewController:UITabBarController
{
    init()
    {
        super.init(nibName:nil, bundle:nil)
    
        // game tab
        let viewMain = MainViewController()
        
        viewMain.title = "my2048"
        
        // setting tab
        let viewSetting = SettingViewController()
        
        viewSetting.title = "setting"
       
        let main = UINavigationController(rootViewController:viewMain)
        let setting = UINavigationController(rootViewController:viewSetting)
        
        self.viewControllers = [
            main, setting
        ]
        
        self.selectedIndex = 0
    
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
