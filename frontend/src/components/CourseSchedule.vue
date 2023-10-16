<template>
    <form>
      <table class="timetable">
        <thead>
          <tr>
            <th></th>
            <th>周一</th>
            <th>周二</th>
            <th>周三</th>
            <th>周四</th>
            <th>周五</th>
            <th>周六</th>
            <th>周日</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th class="time">08:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">09:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">10:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">11:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">13:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">14:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">15:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">16:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">18:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">19:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">20:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
          <tr>
            <th class="time">21:00</th>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
            <td class="cell"></td>
          </tr>
        </tbody>
      </table>
    </form>
  </template>
  
  <style>
    .timetable {
      display: grid;
      grid-template-rows: repeat(12, 1fr);
      grid-template-columns: 1fr repeat(7, 2fr);
      border-collapse: collapse;
      width: 100%;
    }
  
    .timetable th,
    .timetable td {
      border: 1px solid #ccc;
      padding: 0;
      position: relative;
    }
  
    .timetable th {
      background-color: #f5f5f5;
      text-align: center;
      font-weight: normal;
      font-size: 14px;
      color: #666;
    }
  
    .timetable .cell:before {
      content: attr(data-time);
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 12px;
      color: #999;
    }
  
    .timetable .time {
      font-size: 14px;
      color: #666;
      text-align: right;
      padding-right: 10px;
    }
  </style>