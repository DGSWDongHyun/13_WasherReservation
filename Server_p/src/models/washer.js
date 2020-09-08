module.exports = (sequelize, DataTypes) => {
    const washer = sequelize.define('washer', {
      floor: {
        type: DataTypes.STRING(50),
        allowNull: false,
      },
      way: {
        type: DataTypes.STRING(10),
        allowNull: false,
      },
      washerNum: {
        type: DataTypes.STRING(10),
        allowNull: false,
      },
      grade: {
        type: DataTypes.STRING(50),
        allowNull: true,
      },
      classNum: {
        type: DataTypes.STRING(50),
        allowNull: true,
      },
      studentNum: {
        type: DataTypes.STRING(50),
        allowNull: true,
      },
      studentName: {
        type: DataTypes.STRING(40),
        allowNull: true,
      },
      checkWasher: {
        type: DataTypes.STRING(10),
        allowNull: true,
        defaultValue: 'NO',
      },
      washStartTime: {
        type: 'TIMESTAMP',
        allowNull: true,
      },
      washEndTime: {
        type: 'TIMESTAMP',
        allowNull: true,
      }
    }, {
      charset:'utf8',
      collate:'utf8_general_ci',
    });
    return washer;
};