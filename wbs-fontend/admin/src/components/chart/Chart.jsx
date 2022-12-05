import React from 'react';
import "./chart.scss";
import {
  AreaChart,
  Area,
  Legend,
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getStatisticOrder } from '../../redux/statistic/statisticsSlice';
import { getStatisticOrdersApi } from '../../redux/statistic/statisticsApi';

// const data = [
//   { name: "January", Total: 1200 },
//   { name: "February", Total: 2100 },
//   { name: "March", Total: 800 },
//   { name: "April", Total: 1600 },
//   { name: "May", Total: 900 },
//   { name: "June", Total: 1700 },
// ];

const Chart = ({ aspect, title }) => {
  // return (
  //   <div className="chart">
  //     <div className="title">{title}</div>
  //     <ResponsiveContainer width="100%" aspect={aspect}>
  //       <AreaChart
  //         width={730}
  //         height={250}
  //         data={data}
  //         margin={{ top: 10, right: 30, left: 0, bottom: 0 }}
  //       >
  //         <defs>
  //           <linearGradient id="total" x1="0" y1="0" x2="0" y2="1">
  //             <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8} />
  //             <stop offset="95%" stopColor="#8884d8" stopOpacity={0} />
  //           </linearGradient>
  //         </defs>
  //         <XAxis dataKey="name" stroke="gray" />
  //         <CartesianGrid strokeDasharray="3 3" className="chartGrid" />
  //         <Tooltip />
  //         <Area
  //           type="monotone"
  //           dataKey="Total"
  //           stroke="#8884d8"
  //           fillOpacity={1}
  //           fill="url(#total)"
  //         />
  //       </AreaChart>
  //     </ResponsiveContainer>
  //   </div>
  // );
  const dispatch = useDispatch();
  useEffect(() => {
    getStatisticOrdersApi(dispatch, {startDate: '2021-01-01', endDate: '2023-01-01'});
  }, []);
  const statisticOrder = useSelector(state=> state.statistics.statisticOrder.data);
  // const data = [
  //   { name: 'Page A', uv: 4000, pv: 2400, amt: 2400 },
  //   { name: 'Page B', uv: 3000, pv: 1398, amt: 2210 },
  //   { name: 'Page C', uv: 2000, pv: 9800, amt: 2290 },
  //   { name: 'Page D', uv: 2780, pv: 3908, amt: 2000 },
  //   { name: 'Page E', uv: 1890, pv: 4800, amt: 2181 },
  //   { name: 'Page F', uv: 2390, pv: 3800, amt: 2500 },
  //   { name: 'Page G', uv: 3490, pv: 4300, amt: 2100 },
  // ];
  const data = statisticOrder.map(item => ({name: item.dateStatistic, uv: item.totalPrice}))
  return (
        <div className="chart">
      <div className="title">Biểu đồ đặt hàng</div>
    <ResponsiveContainer width="100%" aspect={aspect}>
      <LineChart
        width={600}
        height={300}
        data={data}
        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
      >
        <XAxis dataKey="name" />
        <YAxis />
        <CartesianGrid strokeDasharray="3 3" />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="pv" stroke="#8884d8" activeDot={{ r: 8 }} />
        <Line type="monotone" dataKey="uv" stroke="#82ca9d" />
      </LineChart>
    </ResponsiveContainer>
    </div>
  );
}

export default Chart;
