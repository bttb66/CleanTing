//
//  ChooseCleanerCell.swift
//  CleanTing2
//
//  Created by 김민수 on 2017. 7. 1..
//  Copyright © 2017년 김민수. All rights reserved.
//

import UIKit
import Toaster
class ChooseCleanerCell: UITableViewCell{
    
    
    
    @IBAction func choose_action(_ sender: Any) {
        print("직접선택")
        
        //토스트 메세지
        Toast(text: "서비스 준비 중입니다.").show()
        ToastView.appearance().backgroundColor = UIColor.init(hex: 0xF2D457)
        ToastView.appearance().textColor = UIColor.black
        
    }
    
    
    
    
    
    @IBOutlet weak var name : UILabel!
    
    @IBOutlet weak var choose_cleaner: UIButton!
    
    @IBOutlet weak var imageview: UIImageView!
    
    @IBOutlet weak var star_rating: StarRatingControl!
   
    @IBOutlet weak var age: UILabel!
    
    @IBOutlet weak var activity: UILabel!
  
    @IBOutlet weak var career: UILabel!
    
    @IBOutlet weak var review: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
    }
    
    
}
