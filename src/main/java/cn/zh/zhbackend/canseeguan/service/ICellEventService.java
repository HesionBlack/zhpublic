package cn.zh.zhbackend.canseeguan.service;

import cn.zh.zhbackend.canseeguan.domain.*;

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

    public List<DocumentModel> getQueryDocuments(ListQueryModel query);

    List<BoxModel> getQueryBoxes(ListQueryModel query);

    List<CellMappingModel> searchCellbySpace(ListQueryModel query);

    List<CabinetModel> getCellSummaryLimitSpace(CabinetModel cabinetModel,Double space);
}
