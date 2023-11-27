package cn.codeyang.system.service.impl;

import cn.codeyang.common.core.domain.entity.SysDictData;
import cn.codeyang.common.utils.DictUtils;
import cn.codeyang.system.mapper.SysDictDataMapper;
import cn.codeyang.system.service.ISysDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     *
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return baseMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     *
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return baseMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     *
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return baseMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictData data = selectDictDataById(dictCode);
            baseMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param data 字典数据信息
     *
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData data) {
        int row = baseMapper.insertDictData(data);
        if (row > 0) {
            List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param data 字典数据信息
     *
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData data) {
        int row = baseMapper.updateDictData(data);
        if (row > 0) {
            List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }
}
