import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  constructor(
    public webMessage: WebMessageService
  )
  {

  }

  ngOnInit(): void {
  }

}
