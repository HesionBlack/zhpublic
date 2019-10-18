package cn.zh.zhbackend.canseeguan.service;

import cn.zh.zhbackend.canseeguan.domain.BoxModel;
import cn.zh.zhbackend.canseeguan.domain.CabinetModel;
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
public interface ICellEventService {
    public List<CabinetModel> GetCellSummary(CabinetModel cabinetModel);

    public List<BoxModel> getCellBoxes(CellModel cellModel) throws Exception;
}
