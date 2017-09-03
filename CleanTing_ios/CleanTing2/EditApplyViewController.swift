//
//  EditApplyViewController.swift
//  CleanTing2
//
//  Created by 김민수 on 2017. 7. 6..
//  Copyright © 2017년 김민수. All rights reserved.
//

import UIKit
import Toaster


class EditApplyViewController: UIViewController{
    
    @IBAction func btn_action(_ sender: Any) {
        print("전화 걸기")
        Toast(text: "서비스 준비중입니다.").show()
        ToastView.appearance().backgroundColor = UIColor.init(hex: 0xF2D457)
        ToastView.appearance().textColor = UIColor.black
        
        
    }
    
    @IBOutlet weak var scrollView: UIScrollView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        scrollView.contentSize = CGSize(width: self.view.frame.size.width, height: 500)
        
        
    }
    
    func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }
    
 
        
    
    override func viewWillAppear(_ animated: Bool) {
        //탭바 숨기기
        self.tabBarController?.tabBar.isHidden = true
        
        
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: "dismissKeyboard")
        view.addGestureRecognizer(tap)
        
        
    }
    
    
    
    
}

extension EditApplyViewController{
    
    
    
    
}


