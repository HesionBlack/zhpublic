package cn.zh.zhbackend.canseeguan.domain;

import lombok.Data;

@Data
public class TagModel {
    public String tagKey;

    public String tagId;

    public String tagName;

    public String tagVal;

//        public string TagID { get; set; }//变量ID
//        
//        public string name { get; set; }//变量名称
//        
//        public string comment { get; set; }//备注
//        
//        public string TagType { get; set; }//变量类型
//        
//        public string device { get; set; }//变量所属设备ID
//        
//        public string DeviceName { get; set; }//变量所属设备名称
//        
//        public string AlarmType { get; set; }//报警类型
//        
//        public string AlarmGroup { get; set; }//报警组ID
//        
//        public string AlarmGroupName { get; set; }//报警组名称
//        
//        public string AlarmPri { get; set; }//报警优先级
//        
//        public string LogDataMode { get; set; }//历史记录
//        
//        public string UsageStatis { get; set; }//统计
//        
//        public string RemainValue { get; set; }//值预留
//        
//        public string RemainParam { get; set; }//域预留

}