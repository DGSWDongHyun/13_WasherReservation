const models = require('../../models');
exports.washerRegister = async (req, res) => {
    console.log(req.body);
    const { floor, washerNum, way, grade, classNum, studentNum, studentName, checkWasher, washStartTime, washEndTime } = req.body;
    try {
        const washerData = await models.Washer.findAll({
            where: {
                floor,
                washerNum,
                way,
            },
            raw: true
        });
        if(washerData.length > 0) {
            await models.Washer.update({grade: grade, classNum: classNum, studentNum: studentNum, studentName: studentName, checkWasher: checkWasher, washStartTime: washStartTime, washEndTime: washEndTime}, {where: {floor: floor, washerNum: washerNum, way: way}})
            const result = {
                status: 200,
                message: '세탁기 등록이 완료되었습니다.',
            }
            res.status(200).json(result);
        } else {
            const result = {
                status: 403,
                message: '잘못된 값을 전달했습니다.',
            }
            res.status(403).json(result);
        }
    } catch (error) {
        console.error(error);
        const result = {
            status: 500,
            message: '등록 실패'
        }
        res.status(500).json(result);
    }
};
exports.getWashers = async (req, res) => {
    try {
        const Washerlist = await models.Washer.findAll();
        res.status(200).json(Washerlist);
    } catch (error) {
        console.error(error);
        const result = {
            status: 500,
            message: '조회 실패'
        }
        res.status(500).json(result);
    }
}