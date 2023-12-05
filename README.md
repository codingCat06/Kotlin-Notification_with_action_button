# Kotlin-Notification_with_action_button
## Intent
#### 4개의 컴포넌트들 간의 상호작용(?) 을 중간에서 중계해줌.

## 컴포넌트
### 1) Activity
#### 핸드폰에서 보이는 화면을 의미함.
#  
#  
### 2) Service
#### 백그라운드에서 실행되는 작업을 의미함.
#  
#  
### 3) Broadcast Receiver
#### 특정한 이벤트(?) 구문을 처리함.
#  
#  
### 4) Content Provider
#### 데이터를 관리하고 다른 어플과 데이터를 교환(?)함.
#  
#  
#### 추가 코드
      val intent = Intent(this, Notification_Receiver::class.java).apply {
          putExtra("MESSAGE", "Clicked!")
      }
      val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

      builder.addAction(0, "Action",pendingIntent)
