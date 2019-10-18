package cn.zh.zhbackend.canseeguan.service;

import cn.zh.zhbackend.canseeguan.domain.CellModel;

import java.util.List;

/**
 * ClassName: CellEventService <br/>
 * Description: <br/>
 * date: 2019/10/17 上午9:54<br/>
 *
 * @author Hesion<br />
 * @since JDK 1.8
 */
public interface CellEventService {
    public List<CellModel> GetCellSummary(CellModel cellModel);
}
