package cn.codeyang.system.service.impl;

import cn.codeyang.system.domain.SysNotice;
import cn.codeyang.system.mapper.SysNoticeMapper;
import cn.codeyang.system.service.ISysNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author ruoyi
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     *
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return baseMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     *
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        return baseMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     *
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice) {
        return baseMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     *
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice) {
        return baseMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     *
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId) {
        return baseMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     *
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return baseMapper.deleteNoticeByIds(noticeIds);
    }
}
