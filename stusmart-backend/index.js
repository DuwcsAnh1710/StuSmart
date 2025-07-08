const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
app.use(cors());
app.use(bodyParser.json());

// Thay <username>, <password> bằng thông tin MongoDB Atlas của bạn
const uri = 'mongodb+srv://Minhuy_nnn:huy012230@stusmart.wp8c8q8.mongodb.net/StuSmart?retryWrites=true&w=majority';

mongoose.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true })
    .then(() => console.log('MongoDB connected!'))
    .catch(err => console.log(err));

 // Khởi động server
   const PORT = process.env.PORT || 3000;
   app.listen(PORT, () => {
       console.log(`Server running on port ${PORT}`);
   });

// Định nghĩa schema và model cho Student
const studentSchema = new mongoose.Schema({
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

// API thêm student
app.post('/api/students', async (req, res) => {
    try {
        const student = new Student(req.body);
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

   // Định nghĩa schema và model cho Teacher
   const teacherSchema = new mongoose.Schema({
       firstName: String,
       lastName: String,
       idCard: String,
       gmail: String,
       phone: String
   });
   const Teacher = mongoose.model('Teacher', teacherSchema);

   // API thêm teacher
   app.post('/api/teachers', async (req, res) => {
       try {
           const teacher = new Teacher(req.body);
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