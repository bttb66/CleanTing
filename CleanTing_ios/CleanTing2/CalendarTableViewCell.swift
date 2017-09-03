//
//  CalendarTableViewCell.swift
//  CleanTing2
//
//  Created by 김민수 on 2017. 7. 8..
//  Copyright © 2017년 김민수. All rights reserved.
//

import UIKit

class CalendarTableViewCell: UITableViewCell {

    
    @IBOutlet weak var calendar_label : UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    @IBOutlet weak var action: UIButton!
    
    @IBAction func action_btn(_ sender: Any) {
        
        
        print("test")
    }
    
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
