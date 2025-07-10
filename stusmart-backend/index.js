const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
app.use(cors());
app.use(bodyParser.json());

// Kết nối MongoDB
const uri = 'mongodb+srv://Minhuy_nnn:huy012230@stusmart.wp8c8q8.mongodb.net/StuSmart?retryWrites=true&w=majority';
mongoose.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('MongoDB connected!'))
    .catch(err => console.log(err));
// Thiết lập port cho server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
// Định nghĩa schema và model cho Counter (bộ đếm tự tăng)
const counterSchema = new mongoose.Schema({
    _id: String,
    seq: Number
});
const Counter = mongoose.model('Counter', counterSchema);

// Hàm lấy id tự tăng
async function getNextSequence(name) {
    const ret = await Counter.findByIdAndUpdate(
        name,
        { $inc: { seq: 1 } },
        { new: true, upsert: true }
    );
    return ret.seq;
}

// Định nghĩa schema và model cho Student
const studentSchema = new mongoose.Schema({
    id: Number, // id tự tăng
    username: String,
    password: String,
    className: String,
    fullName: String,
    email: String,
    birthDate: String,
    parentName: String,
    parentPhone: String,
    address: String
});
const Student = mongoose.model('Student', studentSchema);

// API đăng nhập cho học sinh
app.post('/api/students/login', async (req, res) => {
    console.log("Login body:", req.body); // Thêm dòng này
    const { username, password } = req.body;
    try {
        const student = await Student.findOne({ username, password });
        if (!student) return res.status(401).json({ error: 'Sai tài khoản hoặc mật khẩu' });
        res.json(student);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});
// API thêm student
app.post('/api/students', async (req, res) => {
    console.log("AddStudent body:", req.body);
    try {
        const nextId = await getNextSequence('studentid');
        const student = new Student({ ...req.body, id: nextId });
        await student.save();
        res.status(201).json(student);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});// API lấy danh sách student
   app.get('/api/students', async (req, res) => {
       try {
           const students = await Student.find();
           res.json(students);
       } catch (err) {
           res.status(500).json({ error: err.message });
       }
   });
   // Sửa thông tin học sinh theo id
   app.put('/api/students/:id', async (req, res) => {
       try {
           const student = await Student.findByIdAndUpdate(
               req.params.id,
               req.body,
               { new: true } // trả về document đã cập nhật
           );
           if (!student) return res.status(404).json({ error: 'Student not found' });
           res.json(student);
       } catch (err) {
           res.status(400).json({ error: err.message });
       }
   });
   // Xóa học sinh theo id
   app.delete('/api/students/:id', async (req, res) => {
       try {
           const student = await Student.findByIdAndDelete(req.params.id);
           if (!student) return res.status(404).json({ error: 'Student not found' });
           res.json({ message: 'Student deleted' });
       } catch (err) {
           res.status(500).json({ error: err.message });
       }
   });


  // Lấy thông tin học sinh theo id
  app.get('/api/students/:id', async (req, res) => {
      try {
          const student = await Student.findById(req.params.id);
          if (!student) return res.status(404).json({ error: 'Student not found' });
          res.json(student);
      } catch (err) {
          res.status(500).json({ error: err.message });
      }
  });
  // API đăng nhập cho học sinh
  app.post('/api/students/login', async (req, res) => {
      console.log("Login body:", req.body); // Thêm dòng này
      const { username, password } = req.body;
      try {
          const student = await Student.findOne({ username, password });
          if (!student) return res.status(401).json({ error: 'Sai tài khoản hoặc mật khẩu' });
          res.json(student);
      } catch (err) {
          res.status(500).json({ error: err.message });
      }
  });
   // GET /api/students
app.get('/api/students', async (req, res) => {
    const students = await Student.find({}); // Lấy TẤT CẢ học sinh
    res.json(students);
});

   // Định nghĩa schema và model cho Teacher
   const teacherSchema = new mongoose.Schema({
       id: Number, // id tự tăng
       firstName: String,
       lastName: String,
       idCard: String,
       gmail: String,
       phone: String,
       password: String
   });
   const Teacher = mongoose.model('Teacher', teacherSchema);

   // API đăng nhập cho giáo viên
   app.post('/api/teachers/login', async (req, res) => {
       const { gmail, password } = req.body;
       try {
           const teacher = await Teacher.findOne({ gmail, password });
           if (!teacher) return res.status(401).json({ error: 'Sai tài khoản hoặc mật khẩu' });
           res.json(teacher);
       } catch (err) {
           res.status(500).json({ error: err.message });
       }
   });

   // API thêm teacher
   app.post('/api/teachers', async (req, res) => {
       try {
           const nextId = await getNextSequence('teacherid');
           const teacher = new Teacher({ ...req.body, id: nextId });
           await teacher.save();
           res.status(201).json(teacher);
       } catch (err) {
           res.status(400).json({ error: err.message });
       }
   });

   // API lấy danh sách teacher
   app.get('/api/teachers', async (req, res) => {
       try {
           const teachers = await Teacher.find();
           res.json(teachers);
       } catch (err) {
           res.status(500).json({ error: err.message });
       }
   });
   // Lấy chi tiết giáo viên theo id
app.get('/api/teachers/:id', async (req, res) => {
    try {
        const teacher = await Teacher.findById(req.params.id);
        if (!teacher) return res.status(404).json({ error: 'Teacher not found' });
        res.json(teacher);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});
    // Sửa thông tin giáo viên theo id
app.put('/api/teachers/:id', async (req, res) => {
    try {
        const teacher = await Teacher.findByIdAndUpdate(
            req.params.id,
            req.body,
            { new: true }
        );
        if (!teacher) return res.status(404).json({ error: 'Teacher not found' });
        res.json(teacher);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});
// Xóa giáo viên theo id
app.delete('/api/teachers/:id', async (req, res) => {
    try {
        const teacher = await Teacher.findByIdAndDelete(req.params.id);
        if (!teacher) return res.status(404).json({ error: 'Teacher not found' });
        res.json({ message: 'Teacher deleted' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

const AttendanceSchema = new mongoose.Schema({
    studentUsername: String,
    className: String,
    date: String, // hoặc Date nếu muốn
    isPresent: Boolean,
    isAbsent: Boolean
});

const Attendance = require('./models/Attendance');
app.post('/api/attendance', async (req, res) => {
    try {
        console.log('Received attendance:', req.body); // Thêm dòng này
        const records = req.body;
        await Attendance.insertMany(records);
        res.json({ success: true });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});



